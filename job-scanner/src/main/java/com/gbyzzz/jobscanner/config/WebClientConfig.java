package com.gbyzzz.jobscanner.config;

import com.gbyzzz.jobscanner.service.impl.JobSearchText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${bot.cookie}")
    private String COOKIE;
    @Value("${bot.csrf.token}")
    private String CSRF_TOKEN;

    @Bean
    public WebClient webClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(JobSearchText.COOKIE, COOKIE);
                    httpHeaders.set(JobSearchText.CSRF_TOKEN, CSRF_TOKEN);
                })
                .build();
    }
}
