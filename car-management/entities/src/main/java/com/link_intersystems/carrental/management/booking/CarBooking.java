package com.link_intersystems.carrental.management.booking;

public class CarBooking {

    private Integer bookingNumber;
    private String vin;

    public CarBooking(int bookingNumber, String vin) {
        this.bookingNumber = bookingNumber;
        this.vin = vin;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public String getVin() {
        return vin;
    }
}
