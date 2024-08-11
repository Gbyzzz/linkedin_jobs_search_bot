package com.gbyzzz.linkedinjobsbot.service;

public interface KafkaService {
    void sendMessage(String topic, Object message);
}
