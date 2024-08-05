package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SavedJobService {

    List<SavedJob> getJobsByUserId(Long id);
    void saveJob(SavedJob SavedJob);

    List<SavedJob> getAppliedAndDeletedJobsByUserId(Long id);
    List<SavedJob> getAppliedJobsByUserId(Long id);
    List<SavedJob> getNewJobsByUserId(Long id);
    List<SavedJob> getNewJobsByUserIdAndSearchParams(Long id, SearchParams searchParams);

    void saveAll(List<SavedJob> jobs);
    void deleteAll(Set<SavedJob> jobs);

    void saveAllNewJobs(List<String> jobs, Long id);

    SavedJob getJobByIdAndUserId(Long jobId, Long userId);
    boolean existsJobByIdAndUserId(Long jobId, Long userId);

    void delete(SavedJob job);
    int countSavedJobs(Long userId, SavedJob.ReplyState state);
//    Page<SavedJob> getFirstSavedJob(Long userId, SavedJob.ReplyState state);
    int countSavedJobs(Long userId, SavedJob.ReplyState state, Long searchParamsId);
    Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long searchParamsId, Long id);
    Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long id);
    Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long searchParamsId, Long id);
    Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long id);

    Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state, Long searchParamsId);
    Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state);

}
