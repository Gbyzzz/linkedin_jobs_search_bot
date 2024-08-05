package com.gbyzzz.linkedinjobsbot.service;



import com.gbyzzz.linkedinjobsbot.dto.SearchParamsTimeRangeDTO;

public interface JobService {

    void checkIfNew(SearchParamsTimeRangeDTO searchParamsDTO);
}
