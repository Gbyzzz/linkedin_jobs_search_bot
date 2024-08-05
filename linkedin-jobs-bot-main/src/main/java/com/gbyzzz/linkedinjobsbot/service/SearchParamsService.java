package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SearchParamsService {
    SearchParams save(SearchParams searchParams) throws IOException;

    List<SearchParams> findAll();
    SearchParams findById(Long id);
    Boolean existSearchParam(SearchParams searchParams);

    List<SearchParams> findAllByUserId(Long id);

    void delete(SearchParams searchParams);
    Long getCountByUserId(Long userId);
    Optional<SearchParams> findPageByUserId(Long userId, Long id);
}
