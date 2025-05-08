package com.gbyzzz.linkedinjobsbotapi.controller;

import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.JobService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SavedJobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobsController {

    private final SavedJobService savedJobService;
    private final JobService jobService;

    @GetMapping("/all/{id}")
    public List<SavedJob> getAllJobsByUserId(@PathVariable long id) {
        return savedJobService.getJobsByUserId(id);
    }

    @GetMapping("/new/{id}")
    public List<SavedJob> getNewJobsByUserId(@PathVariable long id) {
        return savedJobService.getNewJobsByUserId(id);
    }

    @GetMapping("/applied/{id}")
    public List<SavedJob> getAppliedJobsByUserId(@PathVariable long id) {
        return savedJobService.getJobsByUserId(id);
    }

    @GetMapping("/deleted/{id}")
    public List<SavedJob> getDeletedJobsByUserId(@PathVariable long id) {
        return savedJobService.getJobsByUserId(id);
    }

    @GetMapping("/job/{id}")
    public Job getJobByUserId(@PathVariable long id) {
        return jobService.getJob(id).orElseThrow(RuntimeException::new);
    }
}
