package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.initializer.PostgreSQLContainerInitializer;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository.SavedJobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SavedJobRepositoryTest implements PostgreSQLContainerInitializer {

    @Autowired
    SavedJobRepository savedJobRepository;

    @Test
    void findSavedJobByUserProfile_ChatId() {
        List<SavedJob> savedJobs1 = savedJobRepository.findSavedJobByUserProfile_ChatId(1L);
        List<SavedJob> savedJobs2 = savedJobRepository.findSavedJobByUserProfile_ChatId(2L);
        assertEquals(59, savedJobs1.size());
        assertEquals(41, savedJobs2.size());
    }

    @Test
    void findSavedJobByUserProfile_ChatIdAndReplyState() {
        List<SavedJob> newSavedJobs1 = savedJobRepository
                .findSavedJobByUserProfile_ChatIdAndReplyState(1L, SavedJob.ReplyState.NEW_JOB);
        List<SavedJob> newSavedJobs2 = savedJobRepository
                .findSavedJobByUserProfile_ChatIdAndReplyState(2L, SavedJob.ReplyState.NEW_JOB);
        List<SavedJob> appliedSavedJobs1 = savedJobRepository
                .findSavedJobByUserProfile_ChatIdAndReplyState(1L, SavedJob.ReplyState.APPLIED);
        List<SavedJob> appliedSavedJobs2 = savedJobRepository
                .findSavedJobByUserProfile_ChatIdAndReplyState(2L, SavedJob.ReplyState.APPLIED);

        assertEquals(33, newSavedJobs1.size());
        assertEquals(19, newSavedJobs2.size());
        assertEquals(26, appliedSavedJobs1.size());
        assertEquals(22, appliedSavedJobs2.size());

    }

    @Test
    void existsSavedJobByJobIdAndUserProfileChatId() {
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(1L, 1L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(26L, 1L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(39L, 1L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(47L, 1L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(49L, 2L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(61L, 2L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(81L, 2L));
        assertTrue(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(97L, 2L));

        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(3L, 1L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(7L, 1L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(16L, 1L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(27L, 1L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(34L, 2L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(43L, 2L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(58L, 2L));
        assertFalse(savedJobRepository.existsSavedJobByJobIdAndUserProfileChatId(98L, 2L));

    }

    @Test
    void countSavedJobsByReplyStateAndUserProfile_ChatId() {
        int newJobCount1 = savedJobRepository
                .countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState.NEW_JOB, 1L);
        int newJobCount2 = savedJobRepository
                .countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState.NEW_JOB, 2L);

        int appliedCount1 = savedJobRepository
                .countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState.APPLIED, 1L);
        int appliedCount2 = savedJobRepository
                .countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState.APPLIED, 2L);

        assertEquals(33, newJobCount1);
        assertEquals(19, newJobCount2);
        assertEquals(26, appliedCount1);
        assertEquals(22, appliedCount2);
    }

    @Test
    void countSavedJobByReplyStateAndSearchParams_Id() {
        int newCount1 = savedJobRepository.countSavedJobByReplyStateAndSearchParams_Id(
                SavedJob.ReplyState.NEW_JOB, 1L);
        int newCount2 = savedJobRepository.countSavedJobByReplyStateAndSearchParams_Id(
                SavedJob.ReplyState.NEW_JOB, 2L);
        int newCount3 = savedJobRepository.countSavedJobByReplyStateAndSearchParams_Id(
                SavedJob.ReplyState.NEW_JOB, 3L);
        int newCount4 = savedJobRepository.countSavedJobByReplyStateAndSearchParams_Id(
                SavedJob.ReplyState.NEW_JOB, 4L);

        assertEquals(18, newCount1);
        assertEquals(15, newCount2);
        assertEquals(11, newCount3);
        assertEquals(8, newCount4);
    }

    @Test
    void findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdAsc() {
        Optional<SavedJob> first = savedJobRepository
                .findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdAsc(SavedJob.ReplyState.NEW_JOB,
                        1L, 0L);

        assertEquals(10L, first.get().getJobId());
    }

    @Test
    void findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdAsc() {
        Optional<SavedJob> first = savedJobRepository
                .findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdAsc(1L, SavedJob.ReplyState.NEW_JOB,
                        0L);
        Optional<SavedJob> next = savedJobRepository
                .findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdAsc(1L, SavedJob.ReplyState.NEW_JOB,
                        10L);

        assertEquals(6L, first.get().getJobId());
        assertEquals(15L, next.get().getJobId());

    }

    @Test
    void findTopByReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc() {
        Optional<SavedJob> prev = savedJobRepository
                .findTopByReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc(
                        SavedJob.ReplyState.NEW_JOB, 1L, 87L);
        assertEquals(73L, prev.get().getJobId());
    }

    @Test
    void findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc() {
        Optional<SavedJob> prev = savedJobRepository
                .findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc(1L,
                        SavedJob.ReplyState.NEW_JOB, 87L);

        assertEquals(83L, prev.get().getJobId());
    }

    @Test
    void findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc() {
        Optional<SavedJob> last1 = savedJobRepository
                .findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(
                        SavedJob.ReplyState.NEW_JOB, 1L, 0L);

        Optional<SavedJob> last2 = savedJobRepository
                .findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(
                        SavedJob.ReplyState.NEW_JOB, 1L, 15L);

        assertEquals(88L, last1.get().getJobId());
        assertEquals(88L, last2.get().getJobId());
    }


    @Test
    void findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc() {
        Optional<SavedJob> last1 = savedJobRepository
                .findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(1L,
                        SavedJob.ReplyState.NEW_JOB, 0L);
        Optional<SavedJob> last2 = savedJobRepository
                .findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(1L,
                        SavedJob.ReplyState.NEW_JOB, 0L);

        assertEquals(94L, last1.get().getJobId());
        assertEquals(94L, last2.get().getJobId());
    }
}