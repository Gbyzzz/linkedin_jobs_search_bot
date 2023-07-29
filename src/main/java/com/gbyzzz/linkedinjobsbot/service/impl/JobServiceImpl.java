package com.gbyzzz.linkedinjobsbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbyzzz.linkedinjobsbot.entity.Job;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private static final String COOKIE = "li_sugr=9f1aebdd-3734-49bf-beea-fb9b3cfa8342; AMCVS_14215E3D5995C57C0A495C55%40AdobeOrg=1; JSESSIONID=\"ajax:5674202658019567459\"; timezone=Asia/Jerusalem; _guid=d9c71cde-8030-411c-a172-c716c42051dd; li_theme=dark; li_theme_set=user; liap=true; at_check=true; s_fid=42F67EBCFDDE71B7-0FDD3AA870A7A217; s_cc=true; s_sq=%5B%5BB%5D%5D; s_plt=0.85; s_pltp=developer.linkedin.com%2Fproduct-catalog; gpv_pn=developer.linkedin.com%2F; s_tslv=1689420142370; s_ips=1689; s_tp=2515; s_ppv=developer.linkedin.com%2F%2C67%2C67%2C1689%2C1%2C1; PLAY_LANG=en; _gcl_au=1.1.164304760.1689420575; fid=AQG_0EHRO7ozVwAAAYloEJIo6whASRmKbTjHqpFfinD0wVrefPDDFzdUF0F8I7cz7tdwl2UhUCQkoA; lang=v=2&lang=en-us; bcookie=\"v=2&ddb1ce22-a5f8-48b4-8c83-6b9fc3acd2b3\"; bscookie=\"v=1&20230720071542ccc24876-5a0c-4296-863e-2c3e1543ea83AQHaiRryBBQMlQqsdfC8JsutwjGIzftG\"; li_at=AQEDAQwe1q4C1M9rAAABiXInmuAAAAGJljQe4E0AzNletlIXmsyzVSOi_uoZGb1mySjUl57chdj-IHOzRKn5DgxVQNODgjsY0CIbbn1l09iYB-mNN_-g9WNo8DzpG6sQAkvirhysxHR0Gev5PjCf_azo; PLAY_SESSION=eyJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7InNlc3Npb25faWQiOiIyZmZjNzM3OC1mZjljLTRmZmYtOTA3MS1iZmU2ODhjMDE2NmN8MTY4OTQzNTg4OCIsImFsbG93bGlzdCI6Int9IiwicmVjZW50bHktc2VhcmNoZWQiOiIiLCJyZWZlcnJhbC11cmwiOiIiLCJhaWQiOiIiLCJyZWNlbnRseS12aWV3ZWQiOiI1NDgzNjB8NTQzODUyfDEzNDc0NTgiLCJDUFQtaWQiOiLCglx1MDAwM1x1MDAwM1x1MDAwMSx2QidJe1x1MDAwM8OdPsOMd8KmIiwiZmxvd1RyYWNraW5nSWQiOiJKMmI3ZVF2VFJydUphOFdJaGd2R1l3PT0iLCJleHBlcmllbmNlIjoiZW50aXR5IiwidHJrIjoiIn0sIm5iZiI6MTY4OTgzODA2MiwiaWF0IjoxNjg5ODM4MDYyfQ.ySd8aPk-kyVLKcGBRf5rWBaaG8rV7lc-5FbHssdfAEs; lidc=\"b=VB30:s=V:r=V:a=V:p=V:g=4357:u=464:x=1:i=1689838066:t=1689879817:v=2:sig=AQFoAuw5oUV9p7izHb2hBXwHKwCqACyH\"; li_mc=MTsyMTsxNjg5ODQ0MTI3OzE7MDIxSOcoqVJ9NwNef+rwvMH/ho9xof0CtZYey7N/XTBuEQY=; AMCV_14215E3D5995C57C0A495C55%40AdobeOrg=-637568504%7CMCIDTS%7C19558%7CMCMID%7C60930982390492816921810187840853810855%7CMCOPTOUT-1689851333s%7CNONE%7CvVersion%7C5.1.1; UserMatchHistory=AQIJZxuxWQ5bBgAAAYlykAIiruTNFd_DeXiFosw9KjJ8JpKV2_yUKOcBVEbSu167jFLwtk0d-22lldsg2FHBTcbEiXtjyFdexkHyPFjIGWQuWOiTFVmKrYIV8mNvbdJ-RBDnIcC-HObo9ZjTUwVY8f0cx7JJ2Wxqr6VeKcqzexkNkD8Afi6iG47cPIMqVTSMFscqIYvn0BcwCMAX8jVBZFDCbvbFlfdIh01LWqLNN1qzrjysuAvwd3H3-wxF4MnfXXJuRFwb1kzDqS7yhCTIyo7nyvi4OLh1gx1M4AbF9OHfy2IVSXDacEzPsRM7_Av_y8uBL5VYxMQq9YbjZPBjPIFVLPyg4pM; sdsc=22%3A1%2C1689844340526%7EJAPP%2C0nA%2B%2BKW3hJsM7AOTNGwX4%2F1ODjcY%3D";
    private static final String CSRF_TOKEN = "ajax:5674202658019567459";
    private int totalResults = 0;
    private final JobsRepository jobsRepository;
    private final SearchParamsService searchParamsService;
    private final Executor getJobsTaskExecutor;
    private final EntityManager entityManager;
    private final Executor searchTaskExecutor;
    private static StringBuilder urlBuilder;
    private CopyOnWriteArraySet<String> results = new CopyOnWriteArraySet<>();
    private final ObjectMapper mapper;
    private String location;

    private final SavedJobService savedJobService;


    public JobServiceImpl(JobsRepository jobsRepository,
                          SearchParamsService searchParamsService,
                          @Qualifier("getJobsTaskExecutor") Executor getJobsTaskExecutor,
                          EntityManager entityManager,
                          @Qualifier("searchTaskExecutor") Executor searchTaskExecutor,
                          SavedJobService savedJobService) {
        this.jobsRepository = jobsRepository;
        this.searchParamsService = searchParamsService;
        this.getJobsTaskExecutor = getJobsTaskExecutor;
        this.entityManager = entityManager;
        this.searchTaskExecutor = searchTaskExecutor;
        this.savedJobService = savedJobService;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void makeScan(SearchParams searchParams) throws IOException {
        this.location = location;
        int index = 0;

        urlBuilder = new StringBuilder(URL_SEARCH_JOBS_START);

        if (searchParams.getKeywords().length > 0) {
            StringBuilder keywordsBuilder = new StringBuilder("keywords:");
            keywordsBuilder.append(searchParams.getKeywords()[0]);
            if (searchParams.getKeywords().length > 1) {
                for (int i = 1; i <= searchParams.getKeywords().length; i++) {
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

        if (searchParams.getSearchFilters().size() != 0) {
            StringBuilder filterQuery = new StringBuilder("selectedFilters:(");
            searchParams.getSearchFilters().forEach((key, value) ->
                    filterQuery.append(key).append(":List(").append(value).append("),"));
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
    }

    @Override
    public List<String> filterResults(SearchParams searchParams) {

        String include = "\\b(?:" +
                String.join("|", searchParams.getFilterParams().getInclude())
                + ")\\b";

        String exclude = "^(?!" +
                String.join("|", searchParams.getFilterParams().getExclude())
                + ").*";
        List<String> jobs = jobsRepository.findJobsIncludingAndExcludingWords(include, exclude)
                .stream().map((a) -> a.getId().toString())
                .collect(Collectors.toList());
        System.out.println(jobs.size());

        deleteSavedJobs(jobs, searchParams.getUserProfile().getChatId());

        System.out.println(jobs.size());
        return jobs;
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

    private void deleteSavedJobs(List<String> jobs, Long id){
        List<String> saved = savedJobService.getJobsByUserId(id)
                .stream().map((a) -> a.getJobId().toString())
                .toList();
        jobs.removeAll(saved);
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
                if(optionalJob.isPresent()){
                    locations = optionalJob.get().getSearchLocations();
                }
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
        private final ObjectMapper mapper;

        private final CountDownLatch latch;

        public SearchRequestTask(int index, CountDownLatch latch) {
            this.index = index;
            this.mapper = new ObjectMapper();
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