package com.link_intersystems.carrental.management.pickup.ui;

import javax.swing.*;
import java.time.LocalDateTime;

public class PickupCarModel {

    private String vin;
    private String bookingNumber;
    private LocalDateTime pickupDate;
    private BoundedRangeModel fuelLevel = new DefaultBoundedRangeModel(100, 0, 0, 100);


    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public BoundedRangeModel getFuelLevel() {
        return fuelLevel;
    }
}
