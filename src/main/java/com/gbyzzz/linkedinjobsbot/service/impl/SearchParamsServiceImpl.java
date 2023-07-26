package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.SearchParamsRepository;
import com.gbyzzz.linkedinjobsbot.repository.SearchParamsTempRepository;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchParamsServiceImpl implements SearchParamsService {

    private final RedisTemplate<String, SearchParams> redisTemplate;
    private final SearchParamsRepository searchParamsRepository;
    private final SearchParamsTempRepository searchParamsTempRepository;

    @Override
    public void save(SearchParams searchParams) {
        searchParamsRepository.save(searchParams);
    }

    @Override
    public void saveToTempRepository(SearchParams searchParams, Long chatId) {
        redisTemplate.opsForValue().set(chatId.toString(), searchParams);
//        searchParamsRepository.save(searchParams, chatId);
    }

    @Override
    public SearchParams getFromTempRepository(Long chatId) {
        return redisTemplate.opsForValue().get(chatId.toString());
    }

    @Override
    public void deleteFromTempRepository(Long chatId) {
        redisTemplate.delete(chatId.toString());
    }

    @Override
    public SearchParams findById(Long id) {
        return searchParamsRepository.findById(id).orElseThrow();
    }
}
