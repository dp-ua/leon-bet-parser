package com.dp_ua.leonparser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Runner {
    private String id;
    private String name;
    private boolean open;
    private double price;
    private String priceStr;
    private int r;
    private List<String> tags;

}
