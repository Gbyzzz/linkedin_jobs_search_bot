package com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.impl;


import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository.SavedJobRepository;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SavedJobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;

    @Override
    public List<SavedJob> getJobsByUserId(Long id) {
        return savedJobRepository.findSavedJobByUserProfile_ChatId(id);
    }

    @Override
    public void saveJob(SavedJob savedJob) {
        savedJobRepository.save(savedJob);
    }

    @Override
    public List<SavedJob> getNewJobsByUserId(Long id) {
        return savedJobRepository.findSavedJobByUserProfile_ChatIdAndReplyState(id, SavedJob.ReplyState.NEW_JOB);
    }

    @Override
    public void saveAll(List<SavedJob> jobs) {
        savedJobRepository.saveAll(jobs);
    }

    @Override
    public void deleteAll(Set<SavedJob> jobs) {
       savedJobRepository.deleteAll(jobs);
    }

    @Override
    public boolean existsJobByIdAndUserId(Long jobId, Long userId) {
        return savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(jobId, userId);
    }

    @Override
    public SavedJob getJobById(Long jobId) {
        log.trace("Attempting to find job with ID: {}", jobId);
        return savedJobRepository.findById(jobId).orElseThrow(() -> {
            log.error("Job with ID: {} not found", jobId);
            return new NoSuchElementException("Job with id" + jobId + " not found");
        });
    }

    @Override
    public void delete(SavedJob job) {
        savedJobRepository.delete(job);
    }

    @Override
    public int countSavedJobs(Long userId, SavedJob.ReplyState state) {
        return savedJobRepository.countSavedJobsByReplyStateAndUserProfile_ChatId(state, userId);
    }

    @Override
    public int countSavedJobsBySearchParams(SavedJob.ReplyState state, Long searchParamsId) {
        return savedJobRepository.countSavedJobByReplyStateAndSearchParams_Id(state, searchParamsId);
    }

    @Override
    public Optional<SavedJob> getNextSavedJobBySearchParams(SavedJob.ReplyState state, Long searchParamsId, Long id) {
        return savedJobRepository.findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdAsc(state,
                searchParamsId, id);
    }

    @Override
    public Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long id) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdAsc(userId, state, id);
    }

    @Override
    public Optional<SavedJob> getPrevSavedJobBySearchParams(SavedJob.ReplyState state, Long searchParamsId, Long id) {
        return savedJobRepository.findTopByReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc(state, searchParamsId,
                id);
    }

    @Override
    public Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long id) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc(userId, state, id);
    }

    @Override
    public Optional<SavedJob> getLastSavedJobBySearchParams(SavedJob.ReplyState state, Long searchParamsId) {
        return savedJobRepository.findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(state,
                searchParamsId, 0L);
    }

    @Override
    public Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(
                userId, state, 0L);
    }
}
