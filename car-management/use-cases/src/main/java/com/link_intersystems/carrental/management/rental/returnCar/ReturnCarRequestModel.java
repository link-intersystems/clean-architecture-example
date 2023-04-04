package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.management.rental.FuelLevel;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReturnCarRequestModel {
    private int bookingNumber;
    private LocalDateTime returnDateTime;
    private FuelLevel fuelLevel;
    private Integer odometer;

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    int getBookingNumber() {
        return bookingNumber;
    }

    public void setFuelLevel(FuelLevel fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    FuelLevel getFuelLevel() {
        return fuelLevel;
    }

    Integer getOdometer() {
        return odometer;
    }

    LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnCarRequestModel that = (ReturnCarRequestModel) o;
        return getBookingNumber() == that.getBookingNumber() &&
                Objects.equals(getReturnDateTime(), that.getReturnDateTime()) &&
                getFuelLevel() == that.getFuelLevel() &&
                Objects.equals(getOdometer(), that.getOdometer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookingNumber(), getReturnDateTime(), getFuelLevel(), getOdometer());
    }
}
