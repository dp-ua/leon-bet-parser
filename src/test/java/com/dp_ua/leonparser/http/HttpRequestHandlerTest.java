package com.dp_ua.leonparser.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HttpRequestHandlerTests {
    protected static final String TEST_URI = "/test";
    protected static final int THREAD_COUNT = 3;
    protected static final String SUCCESS = "Success";
    protected static final String TEST_BASE_URL = "https://url.com";

    @Mock
    private WebClient.Builder webClientBuilder;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    private HttpRequestHandler httpRequestHandler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(SUCCESS));

        httpRequestHandler = new HttpRequestHandler(webClientBuilder, TEST_BASE_URL, THREAD_COUNT);
    }

    @Test
    void shouldSetWebClientAndSchedulerWithCorrectParams() {
        verify(webClientBuilder).baseUrl(TEST_BASE_URL);
    }

    @Test
    void fetchDataAsync_ShouldQueryCorrectUrl() {
        Mono<String> result = httpRequestHandler.fetchDataAsync(TEST_URI);

        StepVerifier.create(result)
                .expectNext(SUCCESS)
                .verifyComplete();

        verify(requestHeadersUriSpec).uri(TEST_URI);
    }
}