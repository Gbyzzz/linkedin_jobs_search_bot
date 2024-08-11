package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.IntegrationTestBase;
import com.gbyzzz.linkedinjobsbot.repository.SavedJobRepository;
import com.gbyzzz.linkedinjobsbot.service.SavedJobService;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

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
//        List<String> headerValues = client.getCookies("hasenevich92@mail.ru", "7578722ira");
//        String url = "https://www.linkedin.com/voyager/api/voyagerJobsDashJobCards?decorationId=com.linkedin.voyager.dash.deco.jobs.search.JobSearchCardsCollection-169&count=25&q=jobSearch&query=(origin:JOB_SEARCH_PAGE_LOCATION_AUTOCOMPLETE,keywords:java,locationUnion:(geoId:118503349),spellCorrectionEnabled:true)&start=350";
//        HttpHeaders headers = new HttpHeaders();
////        headers.set("X-Li-User-Agent", "LIAuthLibrary:0.0.3 com.linkedin.android:4.1.881 Asus_ASUS_Z01QD:android_9");
////        headers.set(HttpHeaders.USER_AGENT, "ANDROID OS");
////        headers.set("accept-language", "en-AU,en-GB;q=0.9,en-US;q=0.8,en;q=0.7");
////        headers.set("x-li-lang", "en_US");
////        headers.set("x-restli-protocol-version", "2.0.0");
//        headers.set(HttpHeaders.COOKIE, headerValues.get(0));
//        headers.set("Csrf-Token", headerValues.get(1));
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                url, HttpMethod.GET, requestEntity, String.class);
//        response.getHeaders().get(HttpHeaders.SET_COOKIE).stream().collect(Collectors.joining("; "));
//        client.authenticate("hasenevich92@mail.ru", "7578722ira");
//        HashMap<String, List<String>> map = LinkedInClient.map;
//        System.out.println("map = " + map);
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
//        List<SavedJob> jobs1 = savedJobService.getNewJobsByUserIdAndSearchParams(1L, searchParamsService.findById(1L));
//        List<SavedJob> jobs2 = savedJobService.getNewJobsByUserIdAndSearchParams(1L, searchParamsService.findById(2L));
//        Optional<SavedJob> last = savedJobRepository.findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThanOrderById(
//                1L, SavedJob.ReplyState.NEW_JOB,
//                1L, 0L, Sort.Direction.DESC
//        );
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