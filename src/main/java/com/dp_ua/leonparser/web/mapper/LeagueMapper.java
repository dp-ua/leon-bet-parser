package com.dp_ua.leonparser.web.mapper;

import com.dp_ua.leonparser.web.mapper.response.LeagueResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueMapper {
    private final ObjectMapper objectMapper;

    @SuppressWarnings("java:S112")
    public LeagueResponse mapLeagueResponse(String rawData) {
        try {
            return objectMapper.readValue(
                    rawData,
                    objectMapper.getTypeFactory().constructType(LeagueResponse.class)
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing sports data", e);
        }
    }
}
