package com.gbyzzz.jobscanner.service;


import com.gbyzzz.linkedinjobsbot.modules.commons.dto.SearchParamsTimeRangeDTO;

import java.io.IOException;

public interface JobSearchService {

    void makeScan(SearchParamsTimeRangeDTO searchParams) throws IOException;
}
