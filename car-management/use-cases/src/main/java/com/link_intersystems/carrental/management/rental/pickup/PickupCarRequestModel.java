package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.management.rental.FuelLevel;

import java.time.LocalDateTime;
import java.util.Objects;

public class PickupCarRequestModel {
    private int bookingNumber;
    private LocalDateTime pickupDateTime;
    private FuelLevel fuelLevel;
    private Integer odometer;

    private DriverRequestModel driver;

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    int getBookingNumber() {
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

    FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    Integer getOdometer() {
        return odometer;
    }

    LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PickupCarRequestModel that = (PickupCarRequestModel) o;
        return getBookingNumber() == that.getBookingNumber() &&
                Objects.equals(getPickupDateTime(), that.getPickupDateTime()) &&
                getFuelLevel() == that.getFuelLevel() &&
                Objects.equals(getOdometer(), that.getOdometer()) &&
                Objects.equals(getDriver(), that.getDriver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getBookingNumber(),
                getPickupDateTime(),
                getFuelLevel(),
                getOdometer(),
                getDriver());
    }
}
