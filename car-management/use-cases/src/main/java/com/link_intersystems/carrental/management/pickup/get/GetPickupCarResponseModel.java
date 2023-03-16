package com.link_intersystems.carrental.management.pickup.get;

import com.link_intersystems.carrental.management.pickup.FuelLevel;

import java.time.LocalDateTime;

public class GetPickupCarResponseModel {
    private int bookingNumber;
    private LocalDateTime pickupDate;
    private int odometer;
    private FuelLevel fuelLevel;

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(FuelLevel fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
}
