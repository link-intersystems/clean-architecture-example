package com.link_intersystems.time;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClockProviderTest {

    @Test
    void getDefaultClock() {
        Clock clock = ClockProvider.getClock();
        assertNotNull(clock);
    }

    @Test
    void setClock() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 15, 23, 54);
        Instant intstant = dateTime.toInstant(ZoneOffset.UTC);

        Clock fixedClock = Clock.fixed(intstant, zoneId);
        ClockProvider.setClock(fixedClock);

        assertEquals(fixedClock, ClockProvider.getClock());
    }
}