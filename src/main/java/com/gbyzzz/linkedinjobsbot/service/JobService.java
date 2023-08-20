package com.gbyzzz.linkedinjobsbot.service;

import com.gbyzzz.linkedinjobsbot.entity.SearchParams;

import java.io.IOException;
public interface JobService {

    void makeScan(SearchParams searchParams, Long timePostedRange) throws IOException;
}
