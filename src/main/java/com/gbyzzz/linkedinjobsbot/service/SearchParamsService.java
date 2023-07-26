package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

public interface SearchParamsService {
    void save(SearchParams searchParams);
    void saveToTempRepository(SearchParams searchParams, Long chatId);
    SearchParams getFromTempRepository(Long chatId);

    void deleteFromTempRepository(Long chatId);
    SearchParams findById(Long id);

}
