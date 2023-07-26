package com.gbyzzz.linkedinjobsbot;

import com.gbyzzz.linkedinjobsbot.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.impl.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@SpringBootApplication
@Component
public class LinkedinJobsBotApplication {

    private static JobService jobService;

    public LinkedinJobsBotApplication(JobService jobService) {
        this.jobService = jobService;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LinkedinJobsBotApplication.class, args);

//        jobService.makeInitialScan(new String[]{"java"}, "Israel", new HashMap<>(){{
//            put("experience","1,2");
//            put("timePostedRange","");
//        }},"","");
    }

}
