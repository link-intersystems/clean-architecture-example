package com.link_intersystems.carrental.management.rental.pickup.list;

import com.link_intersystems.carrental.time.LocalDateTimeUtils;

import java.time.LocalDateTime;

public class ListPickupCarResponseModelMock extends ListPickupCarResponseModel {

    @Override
    public void setBookingNumber(int bookingNumber) {
        super.setBookingNumber(bookingNumber);
    }


    public void setPickupDate(String date, String time) {
        setPickupDate(LocalDateTimeUtils.dateTime(date, time));
    }

    @Override
    public void setPickupDate(LocalDateTime pickupDate) {
        super.setPickupDate(pickupDate);
    }

    @Override
    public void setOdometer(int odometer) {
        super.setOdometer(odometer);
    }

}