package com.dp_ua.leonparser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {
    private String id;
    private String name;
    private String nameDefault;
    private String family;
    private String url;
    private List<League> leagues;

}
