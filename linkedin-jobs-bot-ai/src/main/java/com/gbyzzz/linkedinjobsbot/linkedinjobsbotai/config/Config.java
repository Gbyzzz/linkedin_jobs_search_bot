package com.gbyzzz.linkedinjobsbot.linkedinjobsbotai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class Config {

    @Bean
    public ChatClient chatClientLlama(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("ai/llama3.2")
                        .temperature(0.7)
                        .build())
                .build();
    }

    @Bean
    public ChatClient chatClientGemma(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("ai/gemma3")
                        .temperature(0.3)
                        .build())
                .build();
    }

    @Bean
    public ChatClient chatClientMistral(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("ai/mistral")
                        .temperature(0.3)
                        .build())
                .build();
    }

    @Bean
    public RestClient.Builder restClientBuilder(WebClient.Builder webClientBuilder) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory =
                new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(6000));
        return RestClient.builder().requestFactory(simpleClientHttpRequestFactory);
    }
}
