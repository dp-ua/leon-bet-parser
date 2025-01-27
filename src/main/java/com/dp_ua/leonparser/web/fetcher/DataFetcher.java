package com.dp_ua.leonparser.web.fetcher;


import com.dp_ua.leonparser.web.HttpRequestHandler;
import reactor.core.publisher.Mono;

public class DataFetcher {
    private final HttpRequestHandler httpRequestHandler;
    private final String urlTemplate;

    public DataFetcher(HttpRequestHandler httpRequestHandler, String urlTemplate) {
        this.httpRequestHandler = httpRequestHandler;
        this.urlTemplate = urlTemplate;
    }

    public Mono<String> fetchData(String... uriParams) {
        String uri = uriParams.length == 0 ?
                urlTemplate :
                String.format(urlTemplate, (Object[]) uriParams);

        return httpRequestHandler.fetchDataAsync(uri);
    }
}