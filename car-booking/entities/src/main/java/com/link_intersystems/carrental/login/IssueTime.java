package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.time.ClockProvider;

import java.time.*;

import static java.util.Objects.*;

public class IssueTime {

    private LocalDateTime dateTime;

    IssueTime(long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        ZoneId zone = getZoneId();
        this.dateTime = LocalDateTime.ofInstant(instant, zone);
    }

    IssueTime(LocalDateTime dateTime) {
        this.dateTime = requireNonNull(dateTime);
    }

    public long getEpochMilli() {
        ZoneId zone = getZoneId();
        ZonedDateTime issueZonedTime = dateTime.atZone(zone);
        return issueZonedTime.toInstant().toEpochMilli();
    }

    private static ZoneId getZoneId() {
        Clock clock = ClockProvider.getClock();
        return clock.getZone();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
