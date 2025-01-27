package com.dp_ua.leonparser;

import com.dp_ua.leonparser.web.HttpRequestHandler;
import com.dp_ua.leonparser.web.fetcher.DataFetcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Configuration
@Slf4j
public class AppConfig {
    protected static final String CUSTOM_SCHEDULER = "custom-scheduler";
    @Value("${app.events-to-parse}")
    private int eventsToParse;
    @Value("${app.sports-to-parse}")
    private List<String> sportsToParse;
    @Value("${app.connection.thread-count}")
    private int connectionThreadCount;
    @Value("${app.base-url}")
    private String baseUrl;

    @Bean
    public Scheduler scheduler() {
        log.info("Creating Scheduler with connectionThreadCount: {}, name: {}", connectionThreadCount, CUSTOM_SCHEDULER);
        return Schedulers.newBoundedElastic(connectionThreadCount, Integer.MAX_VALUE, CUSTOM_SCHEDULER);
    }

    @Bean
    public ParserConfig parserConfig() {
        log.info("Creating ParserConfig with sportsToParse: {}, eventsToParse: {}", sportsToParse, eventsToParse);
        if (eventsToParse < 1) {
            throw new IllegalArgumentException("topLeaguesToParse must be greater than 0");
        }
        return new ParserConfig(sportsToParse, eventsToParse);
    }

    @Bean
    public HttpRequestHandler httpRequestHandler() {
        log.info("Creating HttpRequestHandler with baseUrl: {}", baseUrl);
        return new HttpRequestHandler(baseUrl, scheduler());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "eventDataFetcher")
    public DataFetcher eventDataFetcher(HttpRequestHandler httpRequestHandler) {
        String eventUrlTemplate = "/api-2/betline/event/all?ctag=en-US&eventId=%s&flags=reg,urlv2,mm2,rrc,nodup,smgv2,outv2";
        return new DataFetcher(httpRequestHandler, eventUrlTemplate);
    }

    @Bean(name = "leagueDataFetcher")
    public DataFetcher leagueDataFetcher(HttpRequestHandler httpRequestHandler) {
        String leagueUrlTemplate = "/api-2/betline/events/all?ctag=en-US&league_id=%s&hideClosed=true&flags=reg,urlv2,mm2,rrc,nodup";
        return new DataFetcher(httpRequestHandler, leagueUrlTemplate);
    }

    @Bean(name = "sportsDataFetcher")
    public DataFetcher sportsDataFetcher(HttpRequestHandler httpRequestHandler) {
        String sportsUrl = "/api-2/betline/sports?ctag=en-US&flags=urlv2";
        return new DataFetcher(httpRequestHandler, sportsUrl);
    }
}
