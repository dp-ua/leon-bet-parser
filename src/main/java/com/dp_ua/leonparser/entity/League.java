package com.dp_ua.leonparser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
    private String id;
    private String name;
    private String nameDefault;
    private String url;
    private int weight;
    private int prematch;
    private int inplay;
    private int outright;
    private boolean top;
    private int topOrder;
    private boolean hasZeroMarginEvents;
    private String logoUrl;
    private Sport sport;
    private Region region;
}

