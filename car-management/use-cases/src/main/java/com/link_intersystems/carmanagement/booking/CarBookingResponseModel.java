package com.link_intersystems.carmanagement.booking;

public class CarBookingResponseModel {
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
