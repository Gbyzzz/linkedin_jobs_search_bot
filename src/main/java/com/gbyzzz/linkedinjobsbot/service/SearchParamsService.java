package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.io.IOException;
import java.util.List;

public interface SearchParamsService {
    SearchParams save(SearchParams searchParams) throws IOException;

    List<SearchParams> findAll();
    SearchParams findById(Long id);
    Boolean existSearchParam(SearchParams searchParams);

    List<SearchParams> findAllByUserId(Long id);

    void delete(SearchParams searchParams);
}
