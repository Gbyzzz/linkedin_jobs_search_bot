package com.gbyzzz.jobscanner.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO;
import com.gbyzzz.jobscanner.entity.Job;
import com.gbyzzz.jobscanner.repository.JobsRepository;
import com.gbyzzz.jobscanner.service.JobService;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Qualifier("JobServiceAsyncImpl")
@Service
public class JobServiceAsyncImpl implements JobService {

    @Value("${bot.cookie}")
    private String COOKIE;
    @Value("${bot.csrf.token}")
    private String CSRF_TOKEN;
    private final WebClient webClient;
    private final JobsRepository jobsRepository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, SearchParamsTimeRangeDTO> kafkaTemplate;

    private final ObjectMapper mapper;

    public JobServiceAsyncImpl(JobsRepository jobsRepository,
                               WebClient webClient, KafkaTemplate<String, SearchParamsTimeRangeDTO> kafkaTemplate) {
        this.jobsRepository = jobsRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = new ObjectMapper();
        this.webClient = webClient;
        this.restTemplate = new RestTemplate();
    }

    @Override
    @KafkaListener(
            topics = "${application.kafka.topic}",
            groupId = "groupId",
            containerFactory = "kafkaListenerContainerFactory")
    public void makeScan(@Payload SearchParamsTimeRangeDTO searchParams) throws IOException {

        String searchUrl = buildSearchUrl(searchParams);
        int totalResults = getTotalResults(searchUrl);
        int min = Math.min(totalResults, 900) / 100;
        List<String> urls = IntStream.range(0, min + 1).mapToObj(i -> searchUrl + i * 100).toList();

//        Flux.fromIterable(urls)
//                .flatMap(page -> webClient.get().uri(page).retrieve().bodyToFlux(String.class))
//                .subscribe(result ->
//                {
//                    List<String> foundIds = getJobsIds(result).stream().filter(id ->
//                            checkIfExists(id, searchParams)).collect(Collectors.toList());
//                    Flux.fromIterable(foundIds)
//                            .log()
//                            .flatMap(id ->
//                            {
//                                System.out.println(id);
//                                return webClient.get()
//                                        .uri(JobSearchText.GET_JOB_START + id + JobSearchText.GET_JOB_END)
//                                        .retrieve()
//                                        .bodyToMono(String.class);
//                            }, 3).subscribe(res ->
//                            {
//                                saveJob(res, searchParams);
//
//                            });
//                },
//                        error -> {
//                        },
//                        () -> {
//                            // Action when the search is completed
//                            System.out.println("Search completed");
//                        });

        Flux.fromIterable(urls)
                .flatMap(page -> webClient.get().uri(page).retrieve().bodyToFlux(String.class))
                .collectList() // Collect all responses into a List
                .flatMap(results -> {
                    List<String> foundIds = results.stream()
                            .flatMap(result -> getJobsIds(result).stream())
                            .filter(id -> checkIfExists(id, searchParams))
                            .collect(Collectors.toList());

                    return Flux.fromIterable(foundIds)
                            .log()
                            .flatMap(id -> {
                                System.out.println(id);
                                return webClient.get()
                                        .uri(JobSearchText.GET_JOB_START + id + JobSearchText.GET_JOB_END)
                                        .retrieve()
                                        .bodyToMono(String.class);
                            }, 3)
                            .collectList(); // Collect all responses from the second set of async requests
                })
                .flatMap(responses -> {
                    // This block runs after all second set of async requests are completed
                    // Save all jobs and return a Mono to indicate completion
                    return Flux.fromIterable(responses)
                            .flatMap(res -> {
                                saveJob(res, searchParams);
                                return Mono.empty();
                            })
                            .then(); // Return Mono<Void> to indicate completion
                })
                .doOnTerminate(() -> {System.out.println("All jobs saved successfully.");
                kafkaTemplate.send("to_check_if_new", String.valueOf(System.currentTimeMillis()),
                        searchParams);}) // Log on termination
                .subscribe(
                        null, // No onNext handler needed
                        error -> System.err.println("Error: " + error)
                );
    }

    private void saveJob(String res, SearchParamsTimeRangeDTO searchParams) {
        Set<String> locations = new HashSet<>();
        Set<Long> searchParamsId = new HashSet<>();
        try {
            Job job = mapper.readValue(res, Job.class);
            locations.add(searchParams.location());
            searchParamsId.add(searchParams.id());
            job.setSearchLocations(locations);
            job.setSearchParamsId(searchParamsId);
            jobsRepository.save(job);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfExists(String id, SearchParamsTimeRangeDTO searchParams) {
        System.out.println(id);
        Optional<Job> optionalJob = jobsRepository.findById(Long.parseLong(id));
        Set<String> locations;
        Set<Long> searchParamsId;
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            if (job.getSearchLocations() != null) {
                locations = optionalJob.get().getSearchLocations();
                locations.add(searchParams.location());
                job.setSearchLocations(locations);
            }
            if (job.getSearchParamsId() != null) {
                searchParamsId = job.getSearchParamsId();
                searchParamsId.add(searchParams.id());
                job.setSearchParamsId(searchParamsId);
            }
            jobsRepository.save(job);
            return false;
        } else {
            return true;
        }
    }

    private String buildSearchUrl(SearchParamsTimeRangeDTO searchParams) {
        String location = searchParams.location();
        StringBuilder urlBuilder = new StringBuilder(JobSearchText.URL_SEARCH_JOBS_START);

        if (searchParams.keywords().length > 0) {
            StringBuilder keywordsBuilder = new StringBuilder(JobSearchText.
                    JOB_SEARCH_QUERY_KEYWORDS);
            keywordsBuilder.append(searchParams.keywords()[0]);
            if (searchParams.keywords().length > 1) {
                for (int i = 1; i < searchParams.keywords().length; i++) {
                    keywordsBuilder.append(JobSearchText.JOB_SEARCH_QUERY_KEYWORDS_SEPARATOR);
                    keywordsBuilder.append(searchParams.keywords()[i]);
                }
            }
            urlBuilder.append(keywordsBuilder).append(JobSearchText.COMMA);
        }

        if (location != null) {
            urlBuilder.append(JobSearchText.JOB_SEARCH_QUERY_LOCATION_START)
                    .append(Locations.valueOf(location.toUpperCase()).getId())
                    .append(JobSearchText.JOB_SEARCH_QUERY_END);
        }

        if (!searchParams.searchFilters().isEmpty()) {
            StringBuilder filterQuery = new StringBuilder(JobSearchText.JOB_SEARCH_QUERY_FILTERS);
            searchParams.searchFilters().forEach((key, value) ->
                    filterQuery.append(key).append(JobSearchText.JOB_SEARCH_QUERY_LIST).append(value)
                            .append(JobSearchText.JOB_SEARCH_QUERY_END));
            if (searchParams.timePostedRange() != null) {
                filterQuery.append(JobSearchText.JOB_SEARCH_QUERY_TIME_RANGE).append(searchParams.timePostedRange())
                        .append(JobSearchText.JOB_SEARCH_QUERY_END);
            }
            filterQuery.replace(filterQuery.length() - 1, filterQuery.length(),
                    JobSearchText.JOB_SEARCH_QUERY_END);
            urlBuilder.append(filterQuery);
        }


        urlBuilder.replace(urlBuilder.length() - 1, urlBuilder.length(),
                JobSearchText.URL_SEARCH_JOBS_END);
        return urlBuilder.toString();
    }

    private int getTotalResults(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(makeRequest((url + JobSearchText.ZERO)));
        return jsonNode.get(JobSearchText.PAGING).get(JobSearchText.TOTAL).asInt();
    }

    private List<String> getJobsIds(String in) {

        JsonNode jsonNode;
        String[] node = null;
        try {
            jsonNode = mapper.readTree(in);
            if (jsonNode.get(JobSearchText.JSON_NODE_METADATA)
                    .has(JobSearchText.JSON_NODE_JOB_CARD_PREFETCH_QUERIES)) {
                node = mapper.convertValue(jsonNode
                        .findPath(JobSearchText.JSON_NODE_PREFETCH_JOB_POSTING_CARD_URNS), String[].class);

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (node != null) {
            return Arrays.stream(node).map(
                            (el) -> el.replaceAll(JobSearchText.NON_NUMERIC, JobSearchText.EMPTY))
                    .toList();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public String makeRequest(String url) {
        // Create an HttpEntity with headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(JobSearchText.COOKIE, COOKIE);
        headers.add(JobSearchText.COOKIE, COOKIE);
        headers.add(JobSearchText.CSRF_TOKEN, CSRF_TOKEN);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the HTTP request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        // Get the response body
        String responseBody = responseEntity.getBody();

        // Return the response body
        return responseBody;
    }

//    private String makeRequest(URL url) throws IOException {
//        BufferedReader in;
//        URL url1 = new URL("https://www.google.com");
//        StringBuilder content = new StringBuilder();
//        HttpsURLConnection con = (HttpsURLConnection) url1.openConnection();
//        con.addRequestProperty(JobSearchText.COOKIE, COOKIE);
//        con.addRequestProperty(JobSearchText.CSRF_TOKEN, CSRF_TOKEN);
//        con.setRequestMethod(JobSearchText.GET);
//        while (content.isEmpty()) {
//            in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//        }
//        return content.toString();
//    }

//    private String makeRequest(URL url) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request.Builder requestBuilder = new Request.Builder()
//                .url(url)
//                .get();
//
//        // Check if COOKIE is not null and add it as a header
//        if (COOKIE != null) {
//            requestBuilder.addHeader(JobSearchText.COOKIE, COOKIE);
//        }
//
//        // Check if CSRF_TOKEN is not null and add it as a header
//        if (CSRF_TOKEN != null) {
//            requestBuilder.addHeader(JobSearchText.CSRF_TOKEN, CSRF_TOKEN);
//        }
//
//        Request request = requestBuilder.build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected response code: " + response);
//            }
//
//            ResponseBody responseBody = response.body();
//            if (responseBody != null) {
//                return responseBody.string();
//            } else {
//                throw new IOException("Response body is null");
//            }
//        }
//    }
}
