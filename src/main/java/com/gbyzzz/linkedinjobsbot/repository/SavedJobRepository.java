package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findSavedJobByUserProfile_ChatId(Long id);
    List<SavedJob> findSavedJobByUserProfile_ChatIdAndReplyState(Long id, SavedJob.ReplyState state);
}
