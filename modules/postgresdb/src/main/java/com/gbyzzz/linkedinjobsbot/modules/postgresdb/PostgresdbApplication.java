package com.gbyzzz.linkedinjobsbot.modules.postgresdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gbyzzz.linkedinjobsbot.modules.postgresdb",
        "com.gbyzzz.linkedinjobsbot.modules.kafka"})
public class PostgresdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostgresdbApplication.class, args);
    }

}
