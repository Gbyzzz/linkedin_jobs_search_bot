package com.gbyzzz.linkedinjobsbot.repository;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchParamsRepository extends JpaRepository<SearchParams, Long> {
    List<SearchParams> findSearchParamsByUserProfile_ChatId(Long id);
}
