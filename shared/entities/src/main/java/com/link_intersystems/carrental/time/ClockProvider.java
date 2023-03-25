package com.link_intersystems.carrental.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockProvider {

    private static Clock clock = Clock.systemDefaultZone();

    public static Clock getClock() {
        return clock;
    }

    public static LocalDateTime now() {
        Clock clock = getClock();
        Instant now = clock.instant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(now, zone);
    }

    static void setClock(Clock clock) {
        ClockProvider.clock = clock;
    }
}
