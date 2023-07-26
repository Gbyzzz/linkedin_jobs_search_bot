package com.gbyzzz.linkedinjobsbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean(name = "searchTaskExecutor")
    public Executor searchTaskExecutor() {
        return Executors.newFixedThreadPool(Math.min(Runtime.getRuntime().availableProcessors(), 10));
    }

    @Bean(name = "getJobsTaskExecutor")
    public Executor getJobsTaskExecutor() {
        return Executors.newFixedThreadPool(Math.min(Runtime.getRuntime().availableProcessors(), 4));
    }
}
