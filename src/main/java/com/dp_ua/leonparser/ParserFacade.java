package com.dp_ua.leonparser;

import com.dp_ua.leonparser.entity.Event;
import com.dp_ua.leonparser.processor.EventProcessor;
import com.dp_ua.leonparser.processor.LeagueProcessor;
import com.dp_ua.leonparser.processor.SportsProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ParserFacade {
    private final SportsProcessor sportsProcessor;
    private final LeagueProcessor leagueProcessor;
    private final EventProcessor eventProcessor;

    public Mono<List<Event>> parseData() {
        return sportsProcessor.processSportsData()
                .flatMap(leagueProcessor::processLeagues)
                .flatMap(eventProcessor::processEvents)
                .doOnError(error -> log.error("Error during final event processing: {}", error.getMessage(), error))
                .doOnSuccess(finalEventList -> log.debug("Completed final event processing"));
    }
}