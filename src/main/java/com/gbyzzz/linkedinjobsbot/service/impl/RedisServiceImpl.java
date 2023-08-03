package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, SearchParams> redisTemplateSearchParams;

    private final RedisTemplate<String, List<String>> redisTemplateList;
    @Override
    public void deleteFromTempRepository(Long chatId) {
        redisTemplateSearchParams.delete(chatId.toString());
    }
    @Override
    public void saveToTempRepository(SearchParams searchParams, Long chatId) {
        redisTemplateSearchParams.opsForValue().set(chatId.toString(), searchParams);
    }
    @Override
    public SearchParams getFromTempRepository(Long chatId) {
        return redisTemplateSearchParams.opsForValue().get(chatId.toString());
    }
}
