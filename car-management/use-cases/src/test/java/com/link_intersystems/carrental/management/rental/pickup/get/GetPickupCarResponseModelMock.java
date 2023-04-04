package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.time.LocalDateTimeUtils;

import java.time.LocalDateTime;

public class GetPickupCarResponseModelMock extends GetPickupCarResponseModel {

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

    @Override
    public void setFuelLevel(FuelLevel fuelLevel) {
        super.setFuelLevel(fuelLevel);
    }


}