package com.link_intersystems.time;

import java.time.*;

public class ClockFactory {

    public Clock getClockAtStartOfDay(LocalDate date) {
        return getClock(date.atStartOfDay());
    }

    public Clock getClock(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset offset = zoneId.getRules().getOffset(dateTime);
        Instant intstant = dateTime.toInstant(offset);
        return Clock.fixed(intstant, zoneId);
    }
}
