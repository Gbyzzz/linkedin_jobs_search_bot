package com.gbyzzz.linkedinjobsbot.modules.postgresdb.service;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.pagination.Pagination;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SavedJobService {

    List<SavedJob> getJobsByUserId(Long id);
    Page<SavedJob> getNewJobsByUserId(Long id, Pagination pagination);
    Page<SavedJob> getAppliedJobsByUserId(Long id, Pagination pagination);
    Page<SavedJob> getRejectedJobsByUserId(Long id, Pagination pagination);
    Page<SavedJob> getDeletedJobsByUserId(Long id, Pagination pagination);

    Page<SavedJob> getJobsByUserIdPage(Long id, Pagination pagination);


    void saveJob(SavedJob SavedJob);
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
    List<SavedJob> getNewJobsByUserIdWhereResultIsNull(Long id);
    List<SavedJob> getAppliedJobsByUserIdWhereResultIsNull(Long id);
}
