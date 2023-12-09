package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SavedJobService {

    List<SavedJob> getJobsByUserId(Long id);
    void saveJob(SavedJob savedJob);

    List<SavedJob> getAppliedAndDeletedJobsByUserId(Long id);
    List<SavedJob> getAppliedJobsByUserId(Long id);
    List<SavedJob> getNewJobsByUserId(Long id);
    List<SavedJob> getNewJobsByUserIdAndSearchParams(Long id, SearchParams searchParams);

    void saveAll(List<SavedJob> jobs);
    void deleteAll(Set<SavedJob> jobs);

    void saveAllNewJobs(List<String> jobs, Long id);

    Optional<SavedJob> getJobByIdAndUserId(Long jobId, Long userId);

    void delete(SavedJob job);
}
