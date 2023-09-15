package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.management.rental.FuelLevel;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReturnCarRequestModel {
    private int bookingNumber;
    private LocalDateTime returnDateTime;
    private FuelLevel fuelLevel;
    private Integer odometer;

    int getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
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

    LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
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
