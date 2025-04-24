package com.gbyzzz.linkedinjobsbotentityservice.service;

import com.gbyzzz.linkedinjobsbotentityservice.entity.SavedJob;

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
    int countSavedJobsBySearchParams(SavedJob.ReplyState state, Long searchParamsId);
    Optional<SavedJob> getNextSavedJobBySearchParams(SavedJob.ReplyState state, Long searchParamsId, Long id);
    Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long id);
    Optional<SavedJob> getPrevSavedJobBySearchParams(SavedJob.ReplyState state, Long searchParamsId, Long id);
    Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long id);

    Optional<SavedJob> getLastSavedJobBySearchParams(SavedJob.ReplyState state, Long searchParamsId);
    Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state);

}
