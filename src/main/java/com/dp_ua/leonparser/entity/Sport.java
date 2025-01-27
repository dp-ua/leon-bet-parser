package com.dp_ua.leonparser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Sport {
    private String id;
    private String name;
    private int weight;
    private String family;
    private List<Region> regions;
}