package com.gbyzzz.linkedinjobsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootApplication
@Component
@EnableScheduling
public class LinkedinJobsBotApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(LinkedinJobsBotApplication.class, args);
    }

}
