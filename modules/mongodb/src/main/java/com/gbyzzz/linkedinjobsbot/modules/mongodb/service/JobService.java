package com.gbyzzz.linkedinjobsbot.modules.mongodb.service;


import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {

    List<Job> findJobsIncludingAndExcludingWords(String include, String exclude, Long searchParamsId);
    Optional<Job> getJob(Long jobId);

    Job save(Job job);
}
