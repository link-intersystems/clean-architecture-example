package com.link_intersystems.carrental.booking;

public class CarBookingResponseModelMock extends CarBookingResponseModel {

    public CarBookingResponseModelMock() {
    }

    public CarBookingResponseModelMock(String bookingNumber) {
        setBookingNumber(bookingNumber);
    }

    public void setBookingNumber(String bookingNumber) {
        super.setBookingNumber(bookingNumber);
    }
}