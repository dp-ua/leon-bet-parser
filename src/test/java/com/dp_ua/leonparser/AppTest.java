package com.dp_ua.leonparser;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class AppTest {
    @Test
    @SuppressWarnings("java:S2699")
    void shouldStartContext() {
        Assert.isTrue(true, "The context should start");
    }
}
