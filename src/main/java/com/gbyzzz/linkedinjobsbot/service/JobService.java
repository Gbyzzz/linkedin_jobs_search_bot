package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JobService {

    void makeScan(SearchParams searchParams) throws IOException;
    List<String> filterResults(SearchParams searchParams);
}
