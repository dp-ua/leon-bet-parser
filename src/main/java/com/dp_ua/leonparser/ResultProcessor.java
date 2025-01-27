package com.dp_ua.leonparser;

import com.dp_ua.leonparser.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ResultProcessor {

    protected static final String LINE_BREAK = "\n";
    protected static final String TAB = "\t";
    protected static final int PRINT_ONLY_FIRST_COUNT_MARKETS = 4;

    @SuppressWarnings("java:S106")
    public Mono<Void> handleParsedEvents(List<Event> events) {
        events.forEach(event -> System.out.println(getEventInfo(event)));
        return Mono.empty();
    }

    private String getEventInfo(Event event) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(event.getLeague().getSport().getName())
                .append(", ")
                .append(event.getLeague().getNameDefault())
                .append(LINE_BREAK)
                .append(TAB)
                .append(event.getName())
                .append(", ")
                .append(event.getStartTime())
                .append(", ")
                .append(event.getId())
                .append(LINE_BREAK);
        event.getMarkets().stream().limit(PRINT_ONLY_FIRST_COUNT_MARKETS)
                .forEach(market -> {
                    sb.append(TAB)
                            .append(TAB)
                            .append(market.getName())
                            .append(LINE_BREAK);
                    market.getRunners().stream().limit(PRINT_ONLY_FIRST_COUNT_MARKETS)
                            .forEach(runner ->
                                    sb.append(TAB)
                                            .append(TAB)
                                            .append(TAB)
                                            .append(runner.getName())
                                            .append(", ")
                                            .append(runner.getPriceStr())
                                            .append(", ")
                                            .append(runner.getId())
                                            .append(LINE_BREAK)
                            );
                });
        return sb.toString();
    }
}
