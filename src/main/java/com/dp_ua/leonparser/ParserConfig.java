package com.dp_ua.leonparser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ParserConfig {
    private final List<String> sportsToParse;
    private final int eventsToParse;
}
