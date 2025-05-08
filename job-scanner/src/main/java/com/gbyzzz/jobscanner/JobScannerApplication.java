package com.gbyzzz.jobscanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.gbyzzz.jobscanner", "com.gbyzzz.linkedinjobsbot"})
@EnableMongoRepositories(basePackages = "com.gbyzzz.linkedinjobsbot.modules.mongodb.repository")
public class JobScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobScannerApplication.class, args);
    }

}
