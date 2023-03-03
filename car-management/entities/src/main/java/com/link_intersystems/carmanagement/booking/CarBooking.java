package com.link_intersystems.carmanagement.booking;

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
