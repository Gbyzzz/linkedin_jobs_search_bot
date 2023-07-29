package com.gbyzzz.linkedinjobsbot;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.impl.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@Component
public class LinkedinJobsBotApplication {

    private static JobService jobService;
    private static SearchParamsService searchParamsService;

    public LinkedinJobsBotApplication(JobService jobService, SearchParamsService searchParamsService) {
        this.jobService = jobService;
        this.searchParamsService = searchParamsService;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LinkedinJobsBotApplication.class, args);

        SearchParams searchParams = searchParamsService.findById(1L);
        List<String> strings = jobService.filterResults(searchParams);
        System.out.println(strings);
//        jobService.makeInitialScan(new String[]{"java"}, "Israel", new HashMap<>(){{
//            put("experience","1,2");
//            put("timePostedRange","");
//        }},"","");
    }

}
