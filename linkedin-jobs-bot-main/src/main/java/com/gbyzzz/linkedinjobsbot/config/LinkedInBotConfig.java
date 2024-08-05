package com.gbyzzz.linkedinjobsbot.config;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import com.gbyzzz.linkedinjobsbot.controller.MessageText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.ExchangeStrategies;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class LinkedInBotConfig {
    @Value("${bot.cookie}")
    private String COOKIE;
    @Value("${bot.csrf.token}")
    private String CSRF_TOKEN;

//    @Bean
//    public TelegramBotsApi telegramBotsApi(LinkedInJobsBot exchangeRatesBot) throws TelegramApiException {
//        var api = new TelegramBotsApi(DefaultBotSession.class);
//        api.registerBot(exchangeRatesBot);
//        return api;
//    }
//
//    @Bean
//    public WebClient webClient() {
//        final int size = 16 * 1024 * 1024;
//        final ExchangeStrategies strategies = ExchangeStrategies.builder()
//                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
//                .build();
//        return WebClient.builder()
//                .exchangeStrategies(strategies)
//                .defaultHeaders(httpHeaders -> {
//                    httpHeaders.set(MessageText.COOKIE, COOKIE);
//                    httpHeaders.set(MessageText.CSRF_TOKEN, CSRF_TOKEN);
//                })
//                .build();
//    }
}
