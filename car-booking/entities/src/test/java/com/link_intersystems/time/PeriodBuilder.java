package com.link_intersystems.time;

import java.time.LocalDateTime;

import static com.link_intersystems.time.LocalDateTimeUtils.*;

public class PeriodBuilder {

    public static PeriodBuilder from(String date, String time) {
        return new PeriodBuilder(dateTime(date, time));
    }

    private LocalDateTime fromDate;

    public PeriodBuilder(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public Period to(String date, String time) {
        LocalDateTime returnDateTime = dateTime(date, time);
        return new Period(fromDate, returnDateTime);
    }
}
