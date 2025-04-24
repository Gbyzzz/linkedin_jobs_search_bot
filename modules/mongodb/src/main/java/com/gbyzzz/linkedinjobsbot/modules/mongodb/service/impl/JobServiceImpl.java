package com.gbyzzz.linkedinjobsbot.modules.mongodb.service.impl;


import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.repository.JobsRepository;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobsRepository jobsRepository;


    @Override
    public List<Job> findJobsIncludingAndExcludingWords(String include, String exclude, Long searchParamsId) {
        return jobsRepository.findJobsIncludingAndExcludingWords(include, exclude, searchParamsId);
    }

    @Override
    public Optional<Job> getJob(Long jobId) {
        return jobsRepository.findJobById(jobId);
    }

    @Override
    public Job save(Job job) {
        return jobsRepository.save(job);
    }
}