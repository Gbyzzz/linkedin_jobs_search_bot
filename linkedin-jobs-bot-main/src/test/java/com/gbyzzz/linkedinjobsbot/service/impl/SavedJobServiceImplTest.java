package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.initializer.PostgreSQLContainerInitializer;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(SavedJobServiceImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SavedJobServiceImplTest implements PostgreSQLContainerInitializer {

    @Autowired
    SavedJobService savedJobService;

    @Test
    void getJobsByUserId() {
        List<SavedJob> jobs1 = savedJobService.getJobsByUserId(1L);
        List<SavedJob> jobs2 = savedJobService.getJobsByUserId(2L);
        List<SavedJob> jobs3 = savedJobService.getJobsByUserId(3L);

        assertEquals(59, jobs1.size());
        assertEquals(41, jobs2.size());
        assertEquals(0, jobs3.size());
    }

    @Test
    void saveJob() {
    }

    @Test
    void getNewJobsByUserId() {
        List<SavedJob> jobs1 = savedJobService.getNewJobsByUserId(1L);
        List<SavedJob> jobs2 = savedJobService.getNewJobsByUserId(2L);
        List<SavedJob> jobs3 = savedJobService.getNewJobsByUserId(3L);

        assertEquals(33, jobs1.size());
        assertEquals(19, jobs2.size());
        assertEquals(0, jobs3.size());
    }

    @Test
    void saveAll() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void existsJobByIdAndUserId() {
        assertTrue(savedJobService.existsJobByIdAndUserId(3L, 2L));
        assertTrue(savedJobService.existsJobByIdAndUserId(2L, 1L));
        assertTrue(savedJobService.existsJobByIdAndUserId(39L, 1L));
        assertTrue(savedJobService.existsJobByIdAndUserId(54L, 2L));

        assertFalse(savedJobService.existsJobByIdAndUserId(65L, 2L));
        assertFalse(savedJobService.existsJobByIdAndUserId(76L, 2L));
        assertFalse(savedJobService.existsJobByIdAndUserId(84L, 1L));
        assertFalse(savedJobService.existsJobByIdAndUserId(97L, 1L));
    }

    @Test
    void getJobById() {
        SavedJob job1 = savedJobService.getJobById(1L);
        SavedJob job2 = savedJobService.getJobById(16L);
        SavedJob job3 = savedJobService.getJobById(49L);
        SavedJob job4 = savedJobService.getJobById(77L);

        assertEquals(1L, job1.getId());
        assertEquals(16L, job2.getId());
        assertEquals(49L, job3.getId());
        assertEquals(77L, job4.getId());

        assertThrows(NoSuchElementException.class, () -> savedJobService.getJobById(110L));
        assertThrows(NoSuchElementException.class, () -> savedJobService.getJobById(-1L));
        assertThrows(NoSuchElementException.class, () -> savedJobService.getJobById(0L));
    }

    @Test
    void delete() {
    }

    @Test
    void countSavedJobs() {
        int newJobCount1 = savedJobService.countSavedJobs(1L, SavedJob.ReplyState.NEW_JOB);
        int appliedCount1 = savedJobService.countSavedJobs(1L, SavedJob.ReplyState.APPLIED);
        int newJobCount2 = savedJobService.countSavedJobs(2L, SavedJob.ReplyState.NEW_JOB);
        int appliedCount2 = savedJobService.countSavedJobs(2L, SavedJob.ReplyState.APPLIED);

        assertEquals(33, newJobCount1);
        assertEquals(26, appliedCount1);
        assertEquals(19, newJobCount2);
        assertEquals(22, appliedCount2);
    }

    @Test
    void countSavedJobsBySearchParams() {
        int newJobCount1 = savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.NEW_JOB, 1L);
        int appliedCount1 = savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.APPLIED, 1L);
        int newJobCount2 = savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.NEW_JOB, 3L);
        int appliedCount2 = savedJobService.countSavedJobsBySearchParams(SavedJob.ReplyState.APPLIED, 3L);

        assertEquals(18, newJobCount1);
        assertEquals(10, appliedCount1);
        assertEquals(11, newJobCount2);
        assertEquals(13, appliedCount2);
    }

    @Test
    void getNextSavedJobBySearchParams() {
    }

    @Test
    void getNextSavedJob() {
    }

    @Test
    void getPrevSavedJobBySearchParams() {
    }

    @Test
    void getPrevSavedJob() {
    }

    @Test
    void getLastSavedJobBySearchParams() {
    }

    @Test
    void getLastSavedJob() {
    }
}