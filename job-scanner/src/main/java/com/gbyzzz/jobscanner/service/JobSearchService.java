package com.gbyzzz.jobscanner.service;

import com.gbyzzz.linkedinjobsbot.modules.dto.dto.SearchParamsTimeRangeDTO;

import java.io.IOException;

public interface JobSearchService {

    void makeScan(SearchParamsTimeRangeDTO searchParams) throws IOException;
}
