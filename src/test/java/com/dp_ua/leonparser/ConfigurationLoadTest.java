package com.dp_ua.leonparser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ConfigurationLoadTest {
    @Autowired
    List<String> sportsToParse;

    @Test
    void shouldLoadConfiguration() {
        assertNotNull(sportsToParse, "Sports to parse should be loaded");
        assertEquals(3, sportsToParse.size(), "Should load 3 sports to parse");
        assertEquals("Sport1", sportsToParse.get(0), "First sport should be football");
        assertEquals("Sport2", sportsToParse.get(1), "Second sport should be basketball");
        assertEquals("Sport3", sportsToParse.get(2), "Third sport should be hockey");

    }
}
