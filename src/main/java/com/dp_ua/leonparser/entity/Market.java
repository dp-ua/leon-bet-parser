package com.dp_ua.leonparser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {
    private int cols;
    private boolean hasZeroMargin;
    private String id;
    private long marketTypeId;
    private String name;
    private boolean open;
    private boolean primary;
    private List<Runner> runners;
    private List<String> selectionTypes;
    private String typeTag;
}
