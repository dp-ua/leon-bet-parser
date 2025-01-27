package com.dp_ua.leonparser.web.mapper.response;

import com.dp_ua.leonparser.entity.Region;
import com.dp_ua.leonparser.entity.Sport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class SportsResponse {
    private String id;
    private String name;
    private long weight;
    private String family;
    private List<Region> regions;
    private List<Sport> sports;
}

