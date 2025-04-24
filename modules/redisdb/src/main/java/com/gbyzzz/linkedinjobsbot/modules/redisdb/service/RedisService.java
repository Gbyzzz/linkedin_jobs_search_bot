package com.gbyzzz.linkedinjobsbot.modules.redisdb.service;


import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;

public interface RedisService {

    void saveToTempRepository(SearchParams searchParams, Long chatId);
    SearchParams getFromTempRepository(Long chatId);
    void deleteFromTempRepository(Long chatId);
}
