package com.gbyzzz.jobscanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gbyzzz.jobscanner", "com.gbyzzz.linkedinjobsbot"})
public class JobScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobScannerApplication.class, args);
    }

}
