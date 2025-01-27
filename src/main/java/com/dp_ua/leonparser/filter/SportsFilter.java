package com.dp_ua.leonparser.filter;


import com.dp_ua.leonparser.ParserConfig;
import com.dp_ua.leonparser.entity.League;
import com.dp_ua.leonparser.web.mapper.response.SportsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class SportsFilter {
    private final ParserConfig config;

    private final Predicate<League> isTopLeague = League::isTop;

    private final Predicate<SportsResponse> isSportToParse = sport -> getSportsToParse().contains(sport.getName());

    public List<League> getTopLeagues(List<SportsResponse> sports) {
        return sports.stream()
                .filter(isSportToParse)
                .flatMap(sport -> sport.getRegions().stream()
                                .flatMap(region -> region.getLeagues().stream()
                                                .filter(isTopLeague)
//                                .map(league -> new SportLeagueInfo(sport.getName(), region.getName(), league))
                                )
                )
                .toList();
    }

    private List<String> getSportsToParse() {
        return config.getSportsToParse();
    }
}