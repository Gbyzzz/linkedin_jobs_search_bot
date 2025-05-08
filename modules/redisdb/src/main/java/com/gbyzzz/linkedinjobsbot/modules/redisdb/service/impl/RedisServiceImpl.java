package com.gbyzzz.linkedinjobsbot.modules.redisdb.service.impl;



import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsDTO;
import com.gbyzzz.linkedinjobsbot.modules.redisdb.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, SearchParamsDTO> redisTemplateSearchParams;

    private final RedisTemplate<String, List<String>> redisTemplateList;
    @Override
    public void deleteFromTempRepository(Long chatId) {
        redisTemplateSearchParams.delete(chatId.toString());
    }
    @Override
    public void saveToTempRepository(SearchParamsDTO searchParams, Long chatId) {
        redisTemplateSearchParams.opsForValue().set(chatId.toString(), searchParams);
    }
    @Override
    public SearchParamsDTO getFromTempRepository(Long chatId) {
        return redisTemplateSearchParams.opsForValue().get(chatId.toString());
    }
}
