package com.link_intersystems.carrental.time;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;

@FunctionalInterface
public interface MutableClock {

    default public void roll(long amountToAdd, TemporalUnit temporalUnit) {
        LocalDateTime now = ClockProvider.now();
        LocalDateTime newTime = now.plus(amountToAdd, temporalUnit);
        set(newTime);
    }

    default public void set(String date, String time) {
        set(dateTime(date, time));
    }

    public void set(LocalDateTime localDateTime);
}
