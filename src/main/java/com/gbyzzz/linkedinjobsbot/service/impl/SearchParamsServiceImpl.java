package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.SearchParamsRepository;
import com.gbyzzz.linkedinjobsbot.repository.SearchParamsTempRepository;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import com.gbyzzz.linkedinjobsbot.service.scheduled.ScheduledService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchParamsServiceImpl implements SearchParamsService {

    private final SearchParamsRepository searchParamsRepository;

    @Override
    public void save(SearchParams searchParams) throws IOException {
        searchParamsRepository.save(searchParams);
    }

    @Override
    public List<SearchParams> findAll() {
        return searchParamsRepository.findAll();
    }

    @Override
    public SearchParams findById(Long id) {
        return searchParamsRepository.findById(id).orElseThrow();
    }
}
