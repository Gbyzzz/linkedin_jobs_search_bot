package com.gbyzzz.jobscanner.config;

import com.gbyzzz.jobscanner.service.impl.JobSearchText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class WebClientConfig {

    @Value("${bot.username}")
    private String username;
    @Value("${bot.password}")
    private String password;

    @Value("${bot.cookie}")
    private String cookie;
    @Value("${bot.csrf.token}")
    private String csrfToken;

    @Bean
    public WebClient webClient() {

        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
//        HashMap<String,String> headers = getCookies(username, password);
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(JobSearchText.COOKIE, cookie);
                    httpHeaders.set(JobSearchText.CSRF_TOKEN, csrfToken);
                })
                .build();
    }

    public HashMap<String, String> getCookies(String username, String password) {
        System.out.println(username);
        System.out.println(password);

        HashMap<String, String> headers = new HashMap<>();
        WebClient client = WebClient.builder().baseUrl("https://www.linkedin.com/uas/authenticate").build();

        Mono<ClientResponse> responseMono1 = client.get()
                .header("X-Li-User-Agent", "LIAuthLibrary:0.0.3 com.linkedin.android:4.1.881 Asus_ASUS_Z01QD:android_9")
                .header(HttpHeaders.USER_AGENT, "ANDROID OS")
                .header("X-User-Language", "en")
                .header("X-User-Locale", "en_US")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-us")
                .exchangeToMono(Mono::just);

        ClientResponse response1 = responseMono1.block();
        System.out.println(response1);
        List<String> cookies = response1.headers().asHttpHeaders().get(HttpHeaders.SET_COOKIE);

        String cookieHeader = cookies != null ? cookies.stream()
                .map(cookie -> cookie.split(";")[0])
                .collect(Collectors.joining("; ")) : "";

        String csrf = Arrays.stream(cookieHeader.split("; "))
                .filter(cookie -> cookie.startsWith("JSESSIONID"))
                .map(cookie -> cookie.split("=")[1].replaceAll("\"", ""))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("JSESSIONID not found"));

        String encodedSessionKey = URLEncoder.encode(username, StandardCharsets.UTF_8);
        String encodedSessionPassword = URLEncoder.encode(password, StandardCharsets.UTF_8);
        String encodedCSRF = URLEncoder.encode(csrf, StandardCharsets.UTF_8);

        String payload = String.format(
                "session_key=%s&session_password=%s&JSESSIONID=%s",
                encodedSessionKey,
                encodedSessionPassword,
                encodedCSRF
        );
        payload+="%22";
        Mono<ClientResponse> responseMono2 = client.post()
                .header("X-Li-User-Agent",
                        "LIAuthLibrary:0.0.3 com.linkedin.android:4.1.881 Asus_ASUS_Z01QD:android_9")
                .header(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(payload.length()))
                .header(HttpHeaders.COOKIE, cookieHeader)
                .header("csrf-token", csrf)
                .bodyValue(payload)
                .exchangeToMono(Mono::just);
        ClientResponse response2 = responseMono2.block();

        String responseCookies = response2.headers().asHttpHeaders()
                .get(HttpHeaders.SET_COOKIE).stream().map(cookie -> cookie.split(";")[0])
                .collect(Collectors.joining("; "));

        headers.put(JobSearchText.COOKIE, responseCookies);
        headers.put(JobSearchText.CSRF_TOKEN, csrf);
        return headers;
    }
}
