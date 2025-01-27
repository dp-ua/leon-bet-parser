package com.dp_ua.leonparser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String id;
    private String name;
    private String url;
    private String kickoff;
    private List<Market> markets;
    private League league;

    public String getStartTime() {
        long milliseconds = Long.parseLong(kickoff);
        Instant instant = Instant.ofEpochMilli(milliseconds);
        ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zdt.format(formatter);
    }
}
