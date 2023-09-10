package com.link_intersystems.carrental.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeUtilsTest {

    @Test
    void dateTime() {
        LocalDateTime localDateTime = LocalDateTimeUtils.dateTime("2023-04-01", "17:23:45");

        LocalDateTime expected = LocalDateTime.of(2023, 4, 1, 17, 23, 45);
        assertEquals(expected, localDateTime);
    }
}