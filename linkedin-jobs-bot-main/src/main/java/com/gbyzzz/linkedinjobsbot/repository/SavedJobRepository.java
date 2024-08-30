package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findSavedJobByUserProfile_ChatId(Long id);

    List<SavedJob> findSavedJobByUserProfile_ChatIdAndReplyState(Long id, SavedJob.ReplyState state);

//    List<SavedJob> findSavedJobByUserProfile_ChatIdAndReplyStateAndSearchParamsContains(Long id,
//                                                                                        SavedJob.ReplyState state,
//                                                                                        SearchParams searchParams);
//
//    Optional<SavedJob> findSavedJobByJobIdAndUserProfileChatId(Long jobId, Long userId);

    boolean existsSavedJobByJobIdAndUserProfileChatId(Long jobId, Long userId);

    int countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState state, Long userId);

    int countSavedJobsByUserProfile_ChatIdAndReplyStateAndSearchParams_Id(Long userId, SavedJob.ReplyState state,
                                                                          Long searchParamsId);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdAsc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long searchParamsId, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdAsc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long searchParamsId, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long searchParamsId, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);
}
