package com.gbyzzz.jobscanner.service.impl;

import com.gbyzzz.jobscanner.dto.SearchParamsTimeRangeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobServiceAsyncImplTest {
    @Autowired
    private JobServiceAsyncImpl jobService;
//
    @Test
    void makeScan() {
//        jobService.getCookies("hasenevich92@mail.ru", "7578722ira");



//        SearchParamsTimeRangeDTO searchParamsTimeRangeDTO = new SearchParamsTimeRangeDTO(1L, new String[]{"java"},
//                "Israel", new HashMap<>() {{
//                    put("experience", "1,2,3,4");
//                    put("jobType", "F,P,C,T,I");
//                    put("workplaceType", "1,2,3");
//                }}, null);
//        jobService.makeScan(searchParamsTimeRangeDTO);
    }
}