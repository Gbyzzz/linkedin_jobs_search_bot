package com.gbyzzz.jobscanner.service;



import com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO;

import java.io.IOException;

public interface JobService {

    void makeScan(SearchParamsTimeRangeDTO searchParams) throws IOException;
}
