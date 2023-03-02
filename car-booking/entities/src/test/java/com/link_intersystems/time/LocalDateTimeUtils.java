package com.link_intersystems.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeUtils {
    public static LocalDateTime dateTime(String date, String time) {
        return LocalDate.parse(date).atTime(LocalTime.parse(time));
    }
}
