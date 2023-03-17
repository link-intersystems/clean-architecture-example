package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.management.rental.FuelLevel;

import java.time.LocalDateTime;

public class PickupCarRequestModel {
    private int bookingNumber;
    private LocalDateTime pickupDateTime;
    private FuelLevel fuelLevel;
    private Integer odometer;

    private DriverRequestModel driver;

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public void setDriver(DriverRequestModel driver) {
        this.driver = driver;
    }

    public void setFuelLevel(FuelLevel fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public DriverRequestModel getDriver() {
        return driver;
    }

    public FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

}
