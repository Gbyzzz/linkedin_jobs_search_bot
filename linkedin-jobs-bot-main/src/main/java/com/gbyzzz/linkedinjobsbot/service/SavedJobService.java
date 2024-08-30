package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SavedJobService {

    List<SavedJob> getJobsByUserId(Long id);
    void saveJob(SavedJob SavedJob);

    List<SavedJob> getNewJobsByUserId(Long id);

    void saveAll(List<SavedJob> jobs);
    void deleteAll(Set<SavedJob> jobs);

    boolean existsJobByIdAndUserId(Long jobId, Long userId);
    SavedJob getJobById(Long jobId);

    void delete(SavedJob job);
    int countSavedJobs(Long userId, SavedJob.ReplyState state);
    int countSavedJobsBySearchParams(Long userId, SavedJob.ReplyState state, Long searchParamsId);
    Optional<SavedJob> getNextSavedJobBySearchParams(Long userId, SavedJob.ReplyState state, Long searchParamsId, Long id);
    Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long id);
    Optional<SavedJob> getPrevSavedJobBySearchParams(Long userId, SavedJob.ReplyState state, Long searchParamsId, Long id);
    Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long id);

    Optional<SavedJob> getLastSavedJobBySearchParams(Long userId, SavedJob.ReplyState state, Long searchParamsId);
    Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state);

}
