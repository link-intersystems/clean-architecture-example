package com.link_intersystems.carrental.time;

import java.time.*;

public class ClockFactory {

    public Clock getClock(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset offset = zoneId.getRules().getOffset(dateTime);
        Instant intstant = dateTime.toInstant(offset);
        return Clock.fixed(intstant, zoneId);
    }
}
