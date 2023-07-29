package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

public interface SearchParamsService {
    void save(SearchParams searchParams);




    SearchParams findById(Long id);

}
