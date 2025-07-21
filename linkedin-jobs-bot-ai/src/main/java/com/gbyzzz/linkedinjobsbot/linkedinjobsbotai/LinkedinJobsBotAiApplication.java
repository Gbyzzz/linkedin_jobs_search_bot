package com.gbyzzz.linkedinjobsbot.linkedinjobsbotai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.gbyzzz.linkedinjobsbot"})
@EnableJpaRepositories(basePackages = "com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository")
@EnableMongoRepositories(basePackages = "com.gbyzzz.linkedinjobsbot.modules.mongodb.repository")
@EntityScan(basePackages = "com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity")
public class LinkedinJobsBotAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkedinJobsBotAiApplication.class, args);
    }

}
