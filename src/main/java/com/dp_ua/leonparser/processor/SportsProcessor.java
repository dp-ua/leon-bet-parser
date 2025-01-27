package com.dp_ua.leonparser.processor;

import com.dp_ua.leonparser.entity.League;
import com.dp_ua.leonparser.filter.SportsFilter;
import com.dp_ua.leonparser.web.fetcher.DataFetcher;
import com.dp_ua.leonparser.web.mapper.SportsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class SportsProcessor {
    private final DataFetcher sportsFetcher;
    private final SportsMapper sportsMapper;
    private final SportsFilter sportsFilter;

    public SportsProcessor(@Qualifier("sportsDataFetcher") DataFetcher sportsFetcher,
                           SportsMapper sportsMapper,
                           SportsFilter sportsFilter) {
        this.sportsFetcher = sportsFetcher;
        this.sportsMapper = sportsMapper;
        this.sportsFilter = sportsFilter;
    }

    public Mono<List<League>> processSportsData() {
        log.debug("Fetching sports data...");
        return sportsFetcher.fetchData()
                .map(sportsMapper::mapSportsResponse)
                .map(sportsFilter::getTopLeagues);
    }
}