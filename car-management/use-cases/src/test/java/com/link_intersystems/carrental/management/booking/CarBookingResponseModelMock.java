package com.link_intersystems.carrental.management.booking;

public class CarBookingResponseModelMock extends CarBookingResponseModel {

    @Override
    public void setCustomer(CustomerResponseModel customer) {
        super.setCustomer(customer);
    }

    @Override
    public void setBookingNumber(Integer bookingNumber) {
        super.setBookingNumber(bookingNumber);
    }

    @Override
    public void setVIN(String VIN) {
        super.setVIN(VIN);
    }
}