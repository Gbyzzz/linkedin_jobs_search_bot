package com.gbyzzz.linkedinjobsbot.modules.postgresdb.service;

import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SearchParamsService {
    SearchParams save(SearchParams searchParams) throws IOException;

    SearchParams findById(Long id);
    Boolean existSearchParam(SearchParams searchParams);

    List<SearchParams> findAllByUserId(Long id);

    void delete(SearchParams searchParams);
    void deleteById(Long searchParamsId);
    int getCountByUserId(Long userId);
    Optional<SearchParams> findNextSearchParams(Long userId, Long id);
    Optional<SearchParams> findPreviousSearchParams(Long userId, Long id);
    Optional<SearchParams> findLastSearchParams(Long userId);
}
