package com.link_intersystems.carrental.time;

import java.time.*;

public class LocalDateTimeUtils {

    public static Clock clockOf(LocalDateTime dateTime) {
        return clockOf(dateTime, ZoneId.systemDefault());
    }

    public static Clock clockOf(LocalDateTime dateTime, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(dateTime);
        Instant intstant = dateTime.toInstant(offset);
        return Clock.fixed(intstant, zoneId);
    }


    public static LocalDateTime dateTime(String date, String time) {
        return LocalDate.parse(date).atTime(LocalTime.parse(time));
    }
}
