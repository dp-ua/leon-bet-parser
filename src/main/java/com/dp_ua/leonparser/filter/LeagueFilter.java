package com.dp_ua.leonparser.filter;

import com.dp_ua.leonparser.ParserConfig;
import com.dp_ua.leonparser.entity.Event;
import com.dp_ua.leonparser.web.mapper.response.LeagueResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeagueFilter {
    private final ParserConfig config;

    public List<Event> getTopEvents(LeagueResponse leagueInfo) {
        return leagueInfo.getEvents()
                .stream()
                .limit(eventsToParse())
                .toList();
    }

    private int eventsToParse() {
        return config.getEventsToParse();
    }
}
