package com.gbyzzz.linkedinjobsbot;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.service.JobService;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.impl.JobServiceImpl;
import com.gbyzzz.linkedinjobsbot.service.impl.SearchParamsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@Component
@EnableScheduling
public class LinkedinJobsBotApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(LinkedinJobsBotApplication.class, args);
    }

}
