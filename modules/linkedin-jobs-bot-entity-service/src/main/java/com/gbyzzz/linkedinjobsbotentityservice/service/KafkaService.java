package com.gbyzzz.linkedinjobsbotentityservice.service;

public interface KafkaService {
    void sendMessage(String topic, Object message);
}
