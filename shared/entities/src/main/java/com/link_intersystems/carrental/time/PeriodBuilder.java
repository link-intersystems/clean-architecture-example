package com.link_intersystems.carrental.time;

import java.time.LocalDateTime;

public class PeriodBuilder {

    private LocalDateTime fromDate;

    public PeriodBuilder(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public static PeriodBuilder from(String date, String time) {
        return new PeriodBuilder(LocalDateTimeUtils.dateTime(date, time));
    }

    public Period to(String date, String time) {
        LocalDateTime returnDateTime = LocalDateTimeUtils.dateTime(date, time);
        return new Period(fromDate, returnDateTime);
    }
}
