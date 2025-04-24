package com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchParamsRepository extends JpaRepository<SearchParams, Long> {
    @EntityGraph(attributePaths = {"filterParams"})
    List<SearchParams> findSearchParamsByUserProfile_ChatId(Long id);
    int countSearchParamsByUserProfile_ChatId(Long userId);
    @EntityGraph(attributePaths = {"filterParams"})
    Optional<SearchParams> findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(Long userId, Long id);
    @EntityGraph(attributePaths = {"filterParams"})
    Optional<SearchParams> findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(Long userId, Long id);
    @EntityGraph(attributePaths = {"filterParams"})
    Optional<SearchParams> findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc(Long userId, Long id);
}
