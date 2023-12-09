package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
