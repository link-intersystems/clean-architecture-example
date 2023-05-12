package com.link_intersystems.carrental.time;

import java.time.*;

public class LocalDateTimeUtils {

    public static Clock clockOf(LocalDateTime dateTime) {
        return clockOf(dateTime, ZoneId.systemDefault());
    }

    public static Clock clockOf(LocalDateTime dateTime, ZoneId zoneId) {
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneId);
        return Clock.fixed(zonedDateTime.toInstant(), zoneId);
    }

    public static Clock clockOf(String date, String time) {
        return clockOf(dateTime(date, time));
    }

    public static LocalDateTime dateTime(String date, String time) {
        return LocalDate.parse(date).atTime(LocalTime.parse(time));
    }
}
