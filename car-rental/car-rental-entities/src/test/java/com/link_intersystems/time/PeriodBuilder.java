package com.link_intersystems.time;

import java.time.LocalDateTime;

import static com.link_intersystems.time.LocalDateTimeUtils.dateTime;

public class PeriodBuilder {

    public static PeriodBuilder pickUpDate(String date, String time) {
        return new PeriodBuilder(dateTime(date, time));
    }

    private LocalDateTime pickUpDateTime;

    public PeriodBuilder(LocalDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    public Period returnDate(String date, String time) {
        LocalDateTime returnDateTime = dateTime(date, time);
        return new Period(pickUpDateTime, returnDateTime);
    }
}
