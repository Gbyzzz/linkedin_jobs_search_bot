package com.gbyzzz.linkedinjobsbotentityservice.service;

import com.gbyzzz.linkedinjobsbotentityservice.entity.SearchParams;

public interface RedisService {

    void saveToTempRepository(SearchParams searchParams, Long chatId);
    SearchParams getFromTempRepository(Long chatId);
    void deleteFromTempRepository(Long chatId);
}
