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

    int getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public DriverRequestModel getDriver() {
        return driver;
    }

    public void setDriver(DriverRequestModel driver) {
        this.driver = driver;
    }

    FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(FuelLevel fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
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
