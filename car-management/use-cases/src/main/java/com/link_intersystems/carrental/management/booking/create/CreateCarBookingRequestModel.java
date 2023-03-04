package com.link_intersystems.carrental.management.booking.create;

public class CreateCarBookingRequestModel {
    private Integer bookingNumber;
    private String vin;

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getVIN() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
