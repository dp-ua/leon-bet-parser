package com.dp_ua.leonparser;

import com.dp_ua.leonparser.http.HttpRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
@Slf4j
public class AppConfig {
    @Value("${app.sports-to-parse}")
    private List<String> sportsToParse;
    @Value("${app.connection.thread-count}")
    private int connectionThreadCount;
    @Value("${app.base-url}")
    private String baseUrl;

    @Bean
    public List<String> sportsToParse() {
        log.info("Sports to parse: {}", sportsToParse);
        return sportsToParse;
    }

    @Bean
    public HttpRequestHandler httpRequestHandler(WebClient.Builder webClientBuilder) {
        log.info("Creating HttpRequestHandler with baseUrl: {}, connectionThreadCount: {}", baseUrl, connectionThreadCount);
        return new HttpRequestHandler(webClientBuilder, baseUrl, connectionThreadCount);
    }
}
