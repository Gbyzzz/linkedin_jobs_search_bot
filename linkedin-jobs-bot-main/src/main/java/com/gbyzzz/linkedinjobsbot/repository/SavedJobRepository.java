package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findSavedJobByUserProfile_ChatId(Long id);

    List<SavedJob> findSavedJobByUserProfile_ChatIdAndReplyState(Long id, SavedJob.ReplyState state);

    List<SavedJob> findSavedJobByUserProfile_ChatIdAndReplyStateAndSearchParamsContains(Long id,
                                                                                        SavedJob.ReplyState state,
                                                                                        SearchParams searchParams);

    Optional<SavedJob> findSavedJobByJobIdAndUserProfileChatId(Long jobId, Long userId);

    boolean existsSavedJobByJobIdAndUserProfileChatId(Long jobId, Long userId);

    int countSavedJobsByReplyStateAndUserProfile_ChatId(SavedJob.ReplyState state, Long userId);

    int countSavedJobsByReplyStateAndSearchParams_IdAndUserProfile_ChatId(SavedJob.ReplyState state, Long userId,
                                                                          Long searchParamsId);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThan(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id, Long searchParamsId);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdGreaterThan(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdLessThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id, Long searchParamsId);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdLessThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndSearchParams_IdAndIdGreaterThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long searchParamsId, Long id);

    Optional<SavedJob> findTopByUserProfileChatIdAndReplyStateAndIdGreaterThanOrderByIdDesc(
            Long userProfileChatId, SavedJob.ReplyState replyState, Long id);
}