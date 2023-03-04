package com.link_intersystems.carrental.time;

import java.time.Clock;

public class ClockProvider {

    private static Clock clock = Clock.systemDefaultZone();

    public static Clock getClock() {
        return clock;
    }

    static void setClock(Clock clock) {
        ClockProvider.clock = clock;
    }
}
