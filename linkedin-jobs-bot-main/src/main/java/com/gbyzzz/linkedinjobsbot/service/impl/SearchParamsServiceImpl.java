package com.gbyzzz.linkedinjobsbot.service.impl;

import com.gbyzzz.linkedinjobsbot.dto.mapper.SearchParamsTimeRangeDTOMapper;
import com.gbyzzz.linkedinjobsbot.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.repository.SearchParamsRepository;
import com.gbyzzz.linkedinjobsbot.service.SearchParamsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SearchParamsServiceImpl implements SearchParamsService {

    private final SearchParamsRepository searchParamsRepository;
    private final SearchParamsTimeRangeDTOMapper searchParamsTimeRangeDTOMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public SearchParams save(SearchParams searchParams) throws IOException {
        SearchParams saved = searchParamsRepository.save(searchParams);
        kafkaTemplate.send("to_search", String.valueOf(System.currentTimeMillis()),
                searchParamsTimeRangeDTOMapper.toDTO(searchParams, null));
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

    @Override
    public Long getCountByUserId(Long userId) {
        return searchParamsRepository.countSearchParamsByUserProfile_ChatId(userId);
    }

    @Override
    public Optional<SearchParams> findPageByUserId(Long userId, Long id) {
        return searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThan(userId, id);
    }
}
