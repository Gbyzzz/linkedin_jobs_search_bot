package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;

import java.util.List;

public interface SavedJobService {

    List<SavedJob> getJobsByUserId(Long id);
    void saveJob(SavedJob savedJob);

}
