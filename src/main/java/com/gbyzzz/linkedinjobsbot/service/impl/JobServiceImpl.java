package com.gbyzzz.linkedinjobsbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbyzzz.linkedinjobsbot.entity.Job;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {


    private static final String URL_SEARCH_JOBS_START = "https://www.linkedin.com/voyager/api/" +
            "voyagerJobsDashJobCards?decorationId=com.linkedin.voyager.dash.deco.jobs.search." +
            "JobSearchCardsCollection-169&count=100&q=jobSearch&query=(origin:" +
            "JOB_SEARCH_PAGE_JOB_FILTER,spellCorrectionEnabled:true,";

    private static final String URL_SEARCH_JOBS_END = ")&start=";
    private static final String GET_JOB_START = "https://www.linkedin.com/voyager/api/jobs/jobPostings/";
    private static final String GET_JOB_END = "?decorationId=com.linkedin.voyager.deco.jobs.web" +
            ".shared.WebFullJobPosting-65&topNRequestedFlavors=List(TOP_APPLICANT,IN_NETWORK," +
            "COMPANY_RECRUIT,SCHOOL_RECRUIT,HIDDEN_GEM,ACTIVELY_HIRING_COMPANY)";
    @Value("${bot.cookie}")
    private String COOKIE;
    @Value("${bot.csrf.token}")
    private String CSRF_TOKEN;
    private int totalResults = 0;
    private final JobsRepository jobsRepository;
    private final Executor getJobsTaskExecutor;
    private final Executor searchTaskExecutor;
    private static StringBuilder urlBuilder;
    private CopyOnWriteArraySet<String> results = new CopyOnWriteArraySet<>();
    private final ObjectMapper mapper;
    private String location;

    private Long searchParamId;
    private List<String> newJobs = new ArrayList<>();

    private final SavedJobService savedJobService;


    public JobServiceImpl(JobsRepository jobsRepository,
                          @Qualifier("getJobsTaskExecutor") Executor getJobsTaskExecutor,
                          @Qualifier("searchTaskExecutor") Executor searchTaskExecutor,
                          SavedJobService savedJobService) {
        this.jobsRepository = jobsRepository;
        this.getJobsTaskExecutor = getJobsTaskExecutor;
        this.searchTaskExecutor = searchTaskExecutor;
        this.savedJobService = savedJobService;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void makeScan(SearchParams searchParams, Long timePostedRange) throws IOException {
        results = new CopyOnWriteArraySet<>();
        totalResults = 0;
        this.location = searchParams.getLocation();
        this.searchParamId = searchParams.getId();

        urlBuilder = new StringBuilder(URL_SEARCH_JOBS_START);

        if (searchParams.getKeywords().length > 0) {
            StringBuilder keywordsBuilder = new StringBuilder("keywords:");
            keywordsBuilder.append(searchParams.getKeywords()[0]);
            if (searchParams.getKeywords().length > 1) {
                for (int i = 1; i < searchParams.getKeywords().length; i++) {
                    keywordsBuilder.append("%20");
                    keywordsBuilder.append(searchParams.getKeywords()[i]);
                }
            }
            urlBuilder.append(keywordsBuilder).append(",");
        }

        if (location != null) {
            urlBuilder.append("locationUnion:(geoId:")
                    .append(Locations.valueOf(location.toUpperCase()).getId())
                    .append("),");
        }

        if (!searchParams.getSearchFilters().isEmpty()) {
            StringBuilder filterQuery = new StringBuilder("selectedFilters:(");
            searchParams.getSearchFilters().forEach((key, value) ->
                    filterQuery.append(key).append(":List(").append(value).append("),"));
            if (timePostedRange != null) {
                filterQuery.append("timePostedRange:List(r").append(timePostedRange).append("),");
            }
            filterQuery.replace(filterQuery.length() - 1, filterQuery.length(), "),");
            urlBuilder.append(filterQuery);
        }

        urlBuilder.replace(urlBuilder.length() - 1, urlBuilder.length(), URL_SEARCH_JOBS_END);
        getTotalResults();


        CountDownLatch searchLatch = new CountDownLatch((int) Math.ceil((double) Math.min(totalResults, 900) / 100));

        for (int i = 0; i < Math.min(totalResults, 900); i = i + 100) {
            Runnable task = new SearchRequestTask(i, searchLatch);
            searchTaskExecutor.execute(task);
        }


        try {
            searchLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> foundIds = new ArrayList<>(results);
        System.out.println(results);
        System.out.println(totalResults);

        CountDownLatch getJobsLatch = new CountDownLatch(results.size());

        for (String jobId : results) {
            Runnable task = new JobRequestTask(jobId, getJobsLatch);
            getJobsTaskExecutor.execute(task);

        }
        try {
            getJobsLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        filterResults(searchParams, foundIds);

    }

    public void filterResults(SearchParams searchParams, List<String> foundIds) {
        System.out.println("Search id: " + searchParams.getId());
        String include = "\\b(?:" +
                String.join("|", searchParams.getFilterParams().getIncludeWordsInDescription())
                + ")\\b";

        String exclude = "^(?:(?!" +
                String.join("|", searchParams.getFilterParams().getExcludeWordsFromTitle())
                + ").)*$\\r?\\n?";
        List<String> jobs = jobsRepository.findJobsIncludingAndExcludingWords(include, exclude)
                .stream().map(job -> job.getId().toString()).collect(Collectors.toList());
        System.out.println(jobs.size());
        jobs.retainAll(foundIds);

        List<SavedJob> jobsToSave = getNewJobsToSave(jobs, searchParams);
        savedJobService.saveAll(jobsToSave);
        System.out.println(jobsToSave.size());
    }

    private void getTotalResults() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(makeRequest(new URL((urlBuilder + "0")
                .replaceAll("count=100", "count=0"))));
        totalResults = jsonNode.get("paging").get("total").asInt();

    }

    private void getJobIds(String in) throws JsonProcessingException {

        JsonNode jsonNode = mapper.readTree(in);

        if (jsonNode.get("metadata").has("jobCardPrefetchQueries")) {
            List<JsonNode> nodes = jsonNode.findValues("jobCardPrefetchQueries");
            String node = jsonNode.findPath("jobCardPrefetchQueries").get(0).get("prefetchJobPostingCardUrns").asText();
            String[] node1 = mapper.convertValue(jsonNode.findPath("prefetchJobPostingCardUrns"), String[].class);
            results.addAll(Arrays.stream(node1).map(
                            (el) -> el.replaceAll("[^0-9]", ""))
                    .toList());
        }
    }

    private String makeRequest(URL url) throws IOException {
        BufferedReader in;
        StringBuilder content = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Cookie", COOKIE);
        con.setRequestProperty("Csrf-Token", CSRF_TOKEN);
        con.setRequestMethod("GET");
        while (content.length() < 1) {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        }
        return content.toString();
    }

    private List<SavedJob> getNewJobsToSave(List<String> jobs, SearchParams searchParams) {
        List<SavedJob> newJobs = new ArrayList<>();
        for (String newJob : jobs) {
            Optional<SavedJob> optionalSavedJob = savedJobService.getJobById(Long.parseLong(newJob));
            if (optionalSavedJob.isPresent()) {
                SavedJob savedJob = optionalSavedJob.get();
                savedJob.getSearchParams().add(searchParams);
                savedJob.getUserProfile().add(searchParams.getUserProfile());
                searchParams.getSavedJobs().add(savedJob);
                newJobs.add(savedJob);
            } else {
                newJobs.add(new SavedJob(Long.parseLong(newJob), SavedJob.ReplyState.NEW_JOB, null,
                        new HashSet<>() {{
                            add(searchParams.getUserProfile());
                        }},
                        new HashSet<>() {{
                            add(searchParams);
                        }}));
            }
        }
        return newJobs;
    }

    class JobRequestTask implements Runnable {

        private final String id;
        private final ObjectMapper mapper;

        private final CountDownLatch latch;


        public JobRequestTask(String id, CountDownLatch latch) {
            this.id = id;
            this.mapper = new ObjectMapper();
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(GET_JOB_START + id + GET_JOB_END);
                String reply = makeRequest(url);
                System.out.println(id);
                Optional<Job> optionalJob = jobsRepository.findById(Long.parseLong(id));
                Set<String> locations = new HashSet<>();
                Set<Long> searchParamsId = new HashSet<>();
                if (optionalJob.isPresent()) {
                    if (optionalJob.get().getSearchLocations() != null) {
                        locations = optionalJob.get().getSearchLocations();
                    }
                    if (optionalJob.get().getSearchParamsId() != null) {
                        searchParamsId = optionalJob.get().getSearchParamsId();
                    }
                } else {
                    newJobs.add(id);
                }
                searchParamsId.add(searchParamId);
                locations.add(location);
                Job job = mapper.readValue(reply, Job.class);
                job.setSearchLocations(locations);
                jobsRepository.save(job);
            } catch (IOException e) {
                throw new RuntimeException(e + "id=" + id);
            } finally {
                latch.countDown();
                System.out.println(latch.getCount());
            }
        }
    }

    class SearchRequestTask implements Runnable {

        private final int index;

        private final CountDownLatch latch;

        public SearchRequestTask(int index, CountDownLatch latch) {
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(urlBuilder.toString() + index);
                String reply = makeRequest(url);
                getJobIds(reply);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
                System.out.println(latch.getCount());
            }
        }
    }
}