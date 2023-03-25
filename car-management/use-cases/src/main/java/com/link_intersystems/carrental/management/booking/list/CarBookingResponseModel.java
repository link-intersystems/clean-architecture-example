package com.link_intersystems.carrental.management.booking.list;

public class CarBookingResponseModel {
    private Integer bookingNumber;
    private String vin;

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getVIN() {
        return vin;
    }

    void setVin(String vin) {
        this.vin = vin;
    }
}
