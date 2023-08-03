package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;

import java.util.List;

public interface SavedJobService {

    List<SavedJob> getJobsByUserId(Long id);
    void saveJob(SavedJob savedJob);

    List<SavedJob> getAppliedAndDeletedJobsByUserId(Long id);
    List<SavedJob> getAppliedJobsByUserId(Long id);
    List<SavedJob> getNewJobsByUserId(Long id);

    void saveAll(List<SavedJob> jobs);

    void saveAllNewJobs(List<String> jobs, Long id);

    SavedJob getJobById(Long jobId);
}
