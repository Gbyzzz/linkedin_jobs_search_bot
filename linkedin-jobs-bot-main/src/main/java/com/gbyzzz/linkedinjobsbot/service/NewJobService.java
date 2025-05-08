package com.gbyzzz.linkedinjobsbot.service;


import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsTimeRangeDTO;

public interface NewJobService {

    void checkIfNew(SearchParamsTimeRangeDTO searchParamsDTO);
}
