package com.dp_ua.leonparser.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

import java.time.Duration;

@Slf4j
public class HttpRequestHandler {
    private final WebClient webClient;
    private final Scheduler scheduler;


    public HttpRequestHandler(String baseUrl, Scheduler scheduler) {
        this.scheduler = scheduler;
        HttpClient httpClient = HttpClient.create()
                .runOn(LoopResources.create("http-client", 1, 1, true))
                .responseTimeout(Duration.ofSeconds(5));

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .defaultHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5")
                .build();
    }

    public Mono<String> fetchDataAsync(String uri) {
        String threadName = Thread.currentThread().getName();
        log.info("THREAD[{}] Fetching data from uri: {}", threadName, uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .subscribeOn(scheduler);
    }
}
