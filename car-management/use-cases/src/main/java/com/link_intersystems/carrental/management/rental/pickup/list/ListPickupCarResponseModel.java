package com.link_intersystems.carrental.management.rental.pickup.list;

import java.time.LocalDateTime;

public class ListPickupCarResponseModel {
    private int bookingNumber;
    private LocalDateTime pickupDate;
    private int odometer;

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
}
