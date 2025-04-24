package com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.impl;

import com.gbyzzz.linkedinjobsbot.modules.kafka.service.KafkaService;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity.SearchParams;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository.SearchParamsRepository;
import com.gbyzzz.linkedinjobsbot.modules.postgresdb.service.SearchParamsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SearchParamsServiceImpl implements SearchParamsService {

    private final SearchParamsRepository searchParamsRepository;
//    private final SearchParamsTimeRangeDTOMapper searchParamsTimeRangeDTOMapper;
    private final KafkaService kafkaService;

    @Override
    public SearchParams save(SearchParams searchParams) throws IOException {
        SearchParams saved = searchParamsRepository.save(searchParams);
//        kafkaService.sendMessage("to_search", searchParamsTimeRangeDTOMapper.toDTO(saved, null));
        return saved;
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
    @Transactional
    public List<SearchParams> findAllByUserId(Long id) {
        return searchParamsRepository.findSearchParamsByUserProfile_ChatId(id);
    }

    @Override
    @Transactional
    public void delete(SearchParams searchParams) {
        searchParamsRepository.delete(searchParams);
    }

    @Override
    public void deleteById(Long searchParamsId) {
        searchParamsRepository.deleteById(searchParamsId);
    }

    @Override
    public int getCountByUserId(Long userId) {
        return searchParamsRepository.countSearchParamsByUserProfile_ChatId(userId);
    }

    @Override
    public Optional<SearchParams> findNextSearchParams(Long userId, Long id) {
        return searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdAsc(userId, id);
    }

    @Override
    public Optional<SearchParams> findPreviousSearchParams(Long userId, Long id) {
        return searchParamsRepository.findTopByUserProfileChatIdAndIdLessThanOrderByIdDesc(userId, id);
    }

    @Override
    public Optional<SearchParams> findLastSearchParams(Long userId) {
        return searchParamsRepository.findTopByUserProfileChatIdAndIdGreaterThanOrderByIdDesc(userId, 0L);
    }
}
