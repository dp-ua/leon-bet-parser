package com.dp_ua.leonparser.processor;

import com.dp_ua.leonparser.entity.Event;
import com.dp_ua.leonparser.web.fetcher.DataFetcher;
import com.dp_ua.leonparser.web.mapper.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;

@Service
@Slf4j
public class EventProcessor {
    private final DataFetcher eventDataFetcher;
    private final EventMapper eventMapper;
    private final Scheduler scheduler;

    public EventProcessor(@Qualifier("eventDataFetcher") DataFetcher eventDataFetcher,
                          EventMapper eventMapper, Scheduler scheduler) {
        this.eventDataFetcher = eventDataFetcher;
        this.eventMapper = eventMapper;
        this.scheduler = scheduler;
    }

    public Mono<List<Event>> processEvents(List<Event> events) {
        return Flux.fromIterable(events)
                .parallel()
                .runOn(scheduler)
                .flatMap(this::processSingleEvent)
                .sequential()
                .collectList();
    }

    private Mono<Event> processSingleEvent(Event event) {
        return eventDataFetcher.fetchData(event.getId())
                .map(eventMapper::mapEventResponse);
    }
}