package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.management.rental.FuelLevel;

import java.time.LocalDateTime;

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

}
