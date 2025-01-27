package com.dp_ua.leonparser.web.mapper;

import com.dp_ua.leonparser.web.mapper.response.SportsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SportsMapper {
    private final ObjectMapper objectMapper;

    @SuppressWarnings("java:S112")
    public List<SportsResponse> mapSportsResponse(String rawData) {
        try {
            return objectMapper.readValue(rawData, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, SportsResponse.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing sports data", e);
        }
    }
}
