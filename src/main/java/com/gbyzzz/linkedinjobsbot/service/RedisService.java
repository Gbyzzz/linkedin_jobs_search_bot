package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.util.List;

public interface RedisService {

    void saveToTempRepository(SearchParams searchParams, Long chatId);
    SearchParams getFromTempRepository(Long chatId);

    void saveToTempRepository(List<String> jobs, Long chatId);
    List<String> getListFromTempRepository(Long chatId);
    void deleteFromTempRepository(Long chatId);
}
