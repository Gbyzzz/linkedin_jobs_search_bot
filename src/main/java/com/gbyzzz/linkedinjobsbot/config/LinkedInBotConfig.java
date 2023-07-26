package com.gbyzzz.linkedinjobsbot.config;

import com.gbyzzz.linkedinjobsbot.controller.LinkedInJobsBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class LinkedInBotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(LinkedInJobsBot exchangeRatesBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(exchangeRatesBot);
        return api;
    }
}
