package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.SearchParamsRepository;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchParamsServiceImpl implements SearchParamsService {

    private final SearchParamsRepository searchParamsRepository;

    @Override
    public SearchParams save(SearchParams searchParams) throws IOException {
        return searchParamsRepository.save(searchParams);
    }

    @Override
    public List<SearchParams> findAll() {
        return searchParamsRepository.findAll();
    }

    @Override
    public SearchParams findById(Long id) {
        return searchParamsRepository.findById(id).orElseThrow();
    }

    @Override
    public Boolean existSearchParam(SearchParams searchParams) {
        List<SearchParams> savedParams = findAllByUserId(searchParams.getUserProfile().getChatId());
        for (SearchParams param : savedParams) {
            if (param.equals(searchParams)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SearchParams> findAllByUserId(Long id) {
        return searchParamsRepository.findSearchParamsByUserProfile_ChatId(id);
    }

    @Override
    @Transactional
    public void delete(SearchParams searchParams) {
        searchParamsRepository.delete(searchParams);
    }
}
