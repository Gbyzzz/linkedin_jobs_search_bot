package com.gbyzzz.linkedinjobsbotentityservice.repository;

import com.gbyzzz.linkedinjobsbotentityservice.entity.SavedJob;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
//    @Query("select s from SavedJob s left join fetch s.userProfile where s.userProfile.chatId = ?1")
    @EntityGraph(attributePaths = {"userProfile"})
    List<SavedJob> findSavedJobByUserProfile_ChatId(Long id);

    @EntityGraph(attributePaths = {"userProfile"})
    List<SavedJob> findSavedJobByUserProfile_ChatIdAndReplyState(Long id, SavedJob.ReplyState state);

    boolean existsSavedJobByJobIdAndUserProfileChatId(Long jobId, Long userId);

    int countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState state, Long userId);

    int countSavedJobByReplyStateAndSearchParams_Id(SavedJob.ReplyState state, Long searchParamsId);

    @EntityGraph(attributePaths = {"userProfile"})
    Optional<SavedJob> findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdAsc(
            SavedJob.ReplyState replyState, Long searchParamsId, Long id);

    @EntityGraph(attributePaths = {"userProfile"})
    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdAsc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);

    @EntityGraph(attributePaths = {"userProfile"})
    Optional<SavedJob> findTopByReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc(SavedJob.ReplyState replyState,

                                                                                       Long searchParamsId, Long id);
    @EntityGraph(attributePaths = {"userProfile"})
    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);

    @EntityGraph(attributePaths = {"userProfile"})
    Optional<SavedJob> findTopByReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(SavedJob.ReplyState replyState,
                                                                                          Long searchParamsId, Long id);

    @EntityGraph(attributePaths = {"userProfile"})
    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(Long userProfileChatId,
                                                                                            SavedJob.ReplyState replyState, Long id);
}
