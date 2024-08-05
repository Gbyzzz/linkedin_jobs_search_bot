package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.IntegrationTestBase;
import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.SavedJobRepository;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SavedJobServiceImplTest extends IntegrationTestBase {

    @Autowired
    SavedJobService savedJobService;

    @Autowired
    SearchParamsService searchParamsService;

    @Autowired
    SavedJobRepository savedJobRepository;

    @Test
    void getJobsByUserId() {
    }

    @Test
    void saveJob() {
    }

    @Test
    void getAppliedAndDeletedJobsByUserId() {
    }

    @Test
    void getAppliedJobsByUserId() {
    }

    @Test
    void getNewJobsByUserId() {
    }

    @Test
    void getNewJobsByUserIdAndSearchParams() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void saveAllNewJobs() {
    }

    @Test
    void getJobByIdAndUserId() {
    }

    @Test
    void existsJobByIdAndUserId() {
    }

    @Test
    void delete() {
    }

    @Test
    void countSavedJobs() {
    }

    @Test
    void getFirstSavedJob() {
    }

    @Test
    void getNextSavedJob() {

//        int ind = 0;
//        int single = 0;
//        long diffInd = 0;
//        long diffSingle = 0;
//        Optional<SavedJob> singleQueryResult1 = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                1L, 0L);
//        Long count  = savedJobService.countSavedJobs(1L, SavedJob.ReplyState.NEW_JOB);
//        Long count1  = savedJobService.countSavedJobs(1L, SavedJob.ReplyState.NEW_JOB, 1L);
//        for (int i = 0; i < 10000; i++) {
//        long startTime = System.nanoTime();
//        SavedJob singleQueryResult = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                1L,  1L, 1);
//        long count = savedJobService.countSavedJobs(SavedJob.ReplyState.NEW_JOB, 1L, 1L);
//        long independentQueriesTime = System.nanoTime() - startTime;
////        System.out.println("Independent queries time: " + independentQueriesTime);
//
//        // Single Request with Page<SavedJob> Approach
//        startTime = System.nanoTime();
//        Page<SavedJob> page = savedJobService.getNextSavedJobPage(1L, SavedJob.ReplyState.NEW_JOB,
//                1L,  1L, 1);
//        long singleRequestTime = System.nanoTime() - startTime;
////        System.out.println("Single request time: " + singleRequestTime);
//        long diff = independentQueriesTime - singleRequestTime;
//        long d = independentQueriesTime/singleRequestTime;
////            System.out.println("diff: " + diff);
////            System.out.println("d: " + d);
//            if(diff>0){
//                single++;
//                diffSingle +=diff;
//            } else {
//                ind++;
//                diffInd +=diff;
//            }
//        }
//        System.out.println("Single: " + single);
//        System.out.println("Independent: " + ind);
//        System.out.println("all jobs");
        List<SavedJob> jobs1 = savedJobService.getNewJobsByUserIdAndSearchParams(1L, searchParamsService.findById(1L));
        List<SavedJob> jobs2 = savedJobService.getNewJobsByUserIdAndSearchParams(1L, searchParamsService.findById(2L));
        Optional<SavedJob> last = savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThanOrderById(
                1L, SavedJob.ReplyState.NEW_JOB,
                1L, 0L, Sort.Direction.DESC
        );
//        System.out.println("page 1");
//        Optional<SavedJob> savedJobPage1 = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                1L, 1L);
//        Optional<SavedJob> savedJobPage2 = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                null, 1L);
//        System.out.println("page 2");
//        Optional<SavedJob> savedJobPage2 = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                1L, 1);
//        System.out.println("page 3");
//        Optional<SavedJob> savedJobPage3 = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                1L, 1);
//        System.out.println("page 4");
//        Optional<SavedJob> savedJobPage4 = savedJobService.getNextSavedJob(1L, SavedJob.ReplyState.NEW_JOB,
//                1L,  1);
//        System.out.println("End");
    }

//    @Test
//    void getPrevSavedJob() {
//        System.out.println("all jobs");
//        List<SavedJob> jobs1 = savedJobService.getNewJobsByUserIdAndSearchParams(1L, searchParamsService.findById(1L));
//        List<SavedJob> jobs2 = savedJobService.getNewJobsByUserIdAndSearchParams(1L, searchParamsService.findById(2L));
//        System.out.println("page 1");
//        SavedJob savedJobPage1 = savedJobService.getPrevSavedJob(1L,SavedJob.ReplyState.NEW_JOB,
//                1L,  100L, 0);
//        System.out.println("page 2");
//        SavedJob savedJobPage2 = savedJobService.getPrevSavedJob(1L,SavedJob.ReplyState.NEW_JOB,
//                1L, savedJobPage1.getJobId(), 1);
//        System.out.println("page 3");
//        SavedJob savedJobPage3 = savedJobService.getPrevSavedJob(1L,SavedJob.ReplyState.NEW_JOB,
//                1L, savedJobPage2.getJobId(), 1);
//        System.out.println("page 4");
//        SavedJob savedJobPage4 = savedJobService.getPrevSavedJob(1L,SavedJob.ReplyState.NEW_JOB,
//                1L, savedJobPage3.getJobId(),  1);
//        System.out.println("End");
//    }

    @Test
    void getLastSavedJob() {
    }
}