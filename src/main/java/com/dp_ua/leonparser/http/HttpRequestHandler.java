package com.dp_ua.leonparser.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class HttpRequestHandler {
    protected static final String HTTP_REQUEST_SCHEDULER = "HttpRequestScheduler";
    protected static final int QUEUED_TASK_CAP = 10;
    private final WebClient webClient;
    private final Scheduler scheduler;

    public HttpRequestHandler(WebClient.Builder webClientBuilder, String baseUrl, int threadCount) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.scheduler = Schedulers.newBoundedElastic(threadCount, QUEUED_TASK_CAP, HTTP_REQUEST_SCHEDULER);
    }

    public Mono<String> fetchDataAsync(String uri) {
        log.debug("Fetching data from uri: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .publishOn(scheduler);
    }
}
