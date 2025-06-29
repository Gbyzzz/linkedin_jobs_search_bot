package com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.service.impl;

import com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.service.AIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AIServiceImplTest {

    @Autowired
    AIServiceImpl aiService;

    @Test
    void test1() {
        aiService.test();
    }
}