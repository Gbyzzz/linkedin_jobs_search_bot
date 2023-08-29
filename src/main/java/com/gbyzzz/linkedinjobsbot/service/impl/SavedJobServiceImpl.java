package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.entity.UserProfile;
import com.gbyzzz.linkedinjobsbot.repository.SavedJobRepository;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final UserProfileService userProfileService;

    @Override
    public List<SavedJob> getJobsByUserId(Long id) {
        return savedJobRepository.findSavedJobByUserProfile_ChatId(id);
    }

    @Override
    public void saveJob(SavedJob savedJob) {
        savedJobRepository.save(savedJob);
    }

    @Override
    public List<SavedJob> getAppliedAndDeletedJobsByUserId(Long id) {
        return savedJobRepository.findSavedJobByUserProfile_ChatIdAndReplyState(id,
                SavedJob.ReplyState.DELETED);
    }

    @Override
    public List<SavedJob> getAppliedJobsByUserId(Long id) {
        return savedJobRepository.findSavedJobByUserProfile_ChatIdAndReplyState(id,
                SavedJob.ReplyState.APPLIED);
    }

    @Override
    public List<SavedJob> getNewJobsByUserId(Long id) {
        return savedJobRepository.findSavedJobByUserProfile_ChatIdAndReplyState(id,
                SavedJob.ReplyState.NEW_JOB);
    }

    @Override
    public List<SavedJob> getNewJobsByUserIdAndSearchParams(Long id, SearchParams searchParams) {
        return savedJobRepository.findSavedJobByUserProfile_ChatIdAndReplyStateAndSearchParamsContains(id,
                SavedJob.ReplyState.NEW_JOB, searchParams);
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
    public void saveAllNewJobs(List<String> jobs, Long id) {
        UserProfile userProfile = userProfileService.getUserProfileById(id).get();
        saveAll(jobs.stream().map(
                (jobId) -> new SavedJob(Long.parseLong(jobId),
                        SavedJob.ReplyState.NEW_JOB, null,
                        new HashSet<>(){{ add(userProfile);}}, null)
                ).toList());
    }

    @Override
    public Optional<SavedJob> getJobById(Long jobId) {
        return savedJobRepository.findById(jobId);
    }

    @Override
    public void delete(SavedJob job) {
        savedJobRepository.delete(job);
    }
}
