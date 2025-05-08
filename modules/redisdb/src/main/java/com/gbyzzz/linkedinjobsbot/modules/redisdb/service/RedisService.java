package com.gbyzzz.linkedinjobsbot.modules.redisdb.service;


import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsDTO;

public interface RedisService {

    void saveToTempRepository(SearchParamsDTO searchParams, Long chatId);
    SearchParamsDTO getFromTempRepository(Long chatId);
    void deleteFromTempRepository(Long chatId);
}
