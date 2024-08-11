package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.repository.SavedJobRepository;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
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
        return savedJobRepository.findById(jobId).orElseThrow();
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
    public int countSavedJobs(Long userId, SavedJob.ReplyState state, Long searchParamsId) {
        return savedJobRepository.countSavedJobsByUserProfile_ChatIdAndReplyStateAndSearchParams_Id(userId, state,
                searchParamsId);
    }

    @Override
    public Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long searchParamsId, Long id) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThan
                (userId, state, searchParamsId, id);
    }

    @Override
    public Optional<SavedJob> getNextSavedJob(Long userId, SavedJob.ReplyState state, Long id) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndIdGreaterThan(userId, state, id);
    }

    @Override
    public Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long searchParamsId, Long id) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc
                (userId, state, searchParamsId, id);
    }

    @Override
    public Optional<SavedJob> getPrevSavedJob(Long userId, SavedJob.ReplyState state, Long id) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc(userId, state, id);
    }

    @Override
    public Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state, Long searchParamsId) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(
                userId, state, searchParamsId, 0L);
    }

    @Override
    public Optional<SavedJob> getLastSavedJob(Long userId, SavedJob.ReplyState state) {
        return savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(
                userId, state, 0L);
    }
}
