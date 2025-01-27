package com.dp_ua.leonparser.processor;

import com.dp_ua.leonparser.entity.Event;
import com.dp_ua.leonparser.entity.League;
import com.dp_ua.leonparser.filter.LeagueFilter;
import com.dp_ua.leonparser.web.fetcher.DataFetcher;
import com.dp_ua.leonparser.web.mapper.LeagueMapper;
import com.dp_ua.leonparser.web.mapper.response.LeagueResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class LeagueProcessor {
    private final DataFetcher leagueFetcher;
    private final LeagueMapper leagueMapper;
    private final LeagueFilter leagueFilter;

    public LeagueProcessor(@Qualifier("leagueDataFetcher") DataFetcher leagueFetcher,
                           LeagueMapper leagueMapper,
                           LeagueFilter leagueFilter) {
        this.leagueFetcher = leagueFetcher;
        this.leagueMapper = leagueMapper;
        this.leagueFilter = leagueFilter;
    }

    public Mono<List<Event>> processLeagues(List<League> leagues) {
        return Flux.fromIterable(leagues)
                .flatMap(this::processSingleLeague)
                .collectList()
                .map(lists -> lists.stream().flatMap(List::stream).toList());
    }

    private Mono<List<Event>> processSingleLeague(League league) {
        return leagueFetcher.fetchData(league.getId())
                .flatMap(result -> {
                    LeagueResponse leagueInfo = leagueMapper.mapLeagueResponse(result);
                    List<Event> topEvents = getTopEvents(leagueInfo);
                    return Mono.just(topEvents);
                });
    }

    private List<Event> getTopEvents(LeagueResponse leagueInfo) {
        return leagueFilter.getTopEvents(leagueInfo);
    }
}
