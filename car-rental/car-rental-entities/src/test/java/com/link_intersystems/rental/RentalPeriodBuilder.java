package com.link_intersystems.rental;

import java.time.LocalDateTime;

import static com.link_intersystems.time.LocalDateTimeUtils.dateTime;

public class RentalPeriodBuilder {

    public static RentalPeriodBuilder pickUpDate(String date, String time) {
        return new RentalPeriodBuilder(dateTime(date, time));
    }

    private LocalDateTime pickUpDateTime;

    public RentalPeriodBuilder(LocalDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    public RentalPeriod returnDate(String date, String time) {
        LocalDateTime returnDateTime = dateTime(date, time);
        return new RentalPeriod(pickUpDateTime, returnDateTime);
    }
}
