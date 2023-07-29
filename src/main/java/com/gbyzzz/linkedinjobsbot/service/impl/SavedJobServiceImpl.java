package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.repository.SavedJobRepository;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
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
}
