package com.link_intersystems.carrental.time;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class FixedClockExtensionTest {

    @Test
    @FixedClock("2023-01-23 08:23:57")
    void test() {
        Clock clock = ClockProvider.getClock();

        Instant instant = clock.instant();
        LocalDateTime clockDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        assertEquals(LocalDateTime.of(2023, 1, 23, 8, 23, 57), clockDateTime);
    }

}
