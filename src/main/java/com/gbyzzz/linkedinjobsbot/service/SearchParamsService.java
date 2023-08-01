package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.io.IOException;
import java.util.List;

public interface SearchParamsService {
    void save(SearchParams searchParams) throws IOException;

    List<SearchParams> findAll();


    SearchParams findById(Long id);

}
