package com.gbyzzz.jobscanner.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbyzzz.jobscanner.service.JobSearchService;
import com.gbyzzz.linkedinjobsbot.modules.commons.dto.SearchParamsTimeRangeDTO;
import com.gbyzzz.linkedinjobsbot.modules.commons.values.Locations;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Qualifier("JobServiceAsyncImpl")
@Service
@AllArgsConstructor
public class JobSearchServiceAsyncImpl implements JobSearchService {

    private final WebClient webClient;

    private final JobService jobService;

    private final KafkaTemplate<String, SearchParamsTimeRangeDTO> kafkaTemplate;

    private final ObjectMapper mapper;

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

        Flux.fromIterable(urls)
                .flatMap(page -> webClient.get().uri(page).retrieve().bodyToFlux(String.class))
                .collectList()
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
                            .collectList();
                })
                .flatMap(responses -> {
                    return Flux.fromIterable(responses)
                            .flatMap(res -> {
                                saveJob(res, searchParams);
                                return Mono.empty();
                            })
                            .then();
                })
                .doOnTerminate(() -> {
                    System.out.println("All jobs saved successfully.");
                    kafkaTemplate.send("to_check_if_new", String.valueOf(System.currentTimeMillis()),
                            searchParams);
                })
                .subscribe(
                        null,
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
            jobService.save(job);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfExists(String id, SearchParamsTimeRangeDTO searchParams) {
        System.out.println(id);
        Optional<Job> optionalJob = jobService.getJob(Long.parseLong(id));
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
            jobService.save(job);
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
        return webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
    }

}
