package com.gbyzzz.linkedinjobsbotapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {"com.gbyzzz.linkedinjobsbot", "com.gbyzzz.linkedinjobsbotapi"})
@EnableJpaRepositories(basePackages = "com.gbyzzz.linkedinjobsbot.modules.postgresdb.repository")
@EnableMongoRepositories(basePackages = "com.gbyzzz.linkedinjobsbot.modules.mongodb.repository")
@EntityScan(basePackages = "com.gbyzzz.linkedinjobsbot.modules.postgresdb.entity")
@Component
public class LinkedinJobsBotApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkedinJobsBotApiApplication.class, args);
    }

}
