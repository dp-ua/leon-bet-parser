package com.dp_ua.leonparser.web.mapper.response;

import com.dp_ua.leonparser.entity.Event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueResponse {
    private long totalCount;
    private String vtag;
    List<Event> events;
}
