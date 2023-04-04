package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.management.rental.FuelLevel;

import java.time.LocalDateTime;

public class GetPickupCarResponseModel {
    private int bookingNumber;
    private LocalDateTime pickupDate;
    private int odometer;
    private FuelLevel fuelLevel;

    void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getOdometer() {
        return odometer;
    }

    void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    void setFuelLevel(FuelLevel fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
}
