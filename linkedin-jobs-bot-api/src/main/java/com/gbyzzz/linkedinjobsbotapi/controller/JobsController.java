package com.gbyzzz.linkedinjobsbotapi.controller;

import com.gbyzzz.linkedinjobsbot.modules.mongodb.entity.Job;
import com.gbyzzz.linkedinjobsbot.modules.mongodb.service.JobService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.pagination.Pagination;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SavedJobService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class JobsController {

    private final SavedJobService savedJobService;
    private final JobService jobService;

    @PostMapping("/all/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Page<SavedJob>> getAllJobsByUserId(@PathVariable long id,
                                                             @RequestBody Pagination pagination) {
        return ResponseEntity.ok(savedJobService.getJobsByUserIdPage(id, pagination));
    }

    @PostMapping("/new_job/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Page<SavedJob>> getNewJobsByUserId(@PathVariable long id,
                                             @RequestBody Pagination pagination) {
        return ResponseEntity.ok(savedJobService.getNewJobsByUserId(id, pagination));
    }

    @PostMapping("/applied/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Page<SavedJob>> getAppliedJobsByUserId(@PathVariable long id,
                                                                 @RequestBody Pagination pagination) {
        return ResponseEntity.ok(savedJobService.getAppliedJobsByUserId(id, pagination));
    }

    @PostMapping("/deleted/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Page<SavedJob>> getDeletedJobsByUserId(@PathVariable long id,
                                                                 @RequestBody Pagination pagination) {
        return ResponseEntity.ok(savedJobService.getDeletedJobsByUserId(id, pagination));
    }

    @PostMapping("/rejected/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Page<SavedJob>> getRejectedJobsByUserId(@PathVariable long id,
                                                                  @RequestBody Pagination pagination) {
        return ResponseEntity.ok(savedJobService.getRejectedJobsByUserId(id, pagination));
    }

    @GetMapping("/job/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN') or #id == authentication.principal.id")
    public Job getJobByUserId(@PathVariable long id) {
        return jobService.getJob(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/save_all")
    @PreAuthorize("hasAnyAuthority('ADMIN') or #id == authentication.principal.id")
    public void saveAllJobs(@RequestBody List<SavedJob> jobs) {
        savedJobService.saveAll(jobs);
    }
}
