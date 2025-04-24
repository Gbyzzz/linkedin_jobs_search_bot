package com.gbyzzz.linkedinjobsbot.modules.kafka.service;

public interface KafkaService {
    void sendMessage(String topic, Object message);
}
