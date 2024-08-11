package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SavedJob;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchParamsRepository extends JpaRepository<SearchParams, Long> {
    List<SearchParams> findSearchParamsByUserProfile_ChatId(Long id);
    int countSearchParamsByUserProfile_ChatId(Long userId);
    Optional<SearchParams> findTopByUserProfileChatIdAndIdGreaterThan(Long userId, Long id);
    Optional<SearchParams> findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(Long userId, Long id);
    Optional<SearchParams> findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc(Long userId, Long id);
}
