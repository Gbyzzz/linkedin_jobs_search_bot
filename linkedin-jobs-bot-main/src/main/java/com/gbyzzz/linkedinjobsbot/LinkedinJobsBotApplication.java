package com.gbyzzz.linkedinjobsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
@EnableScheduling
public class LinkedinJobsBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkedinJobsBotApplication.class, args);
    }
}
