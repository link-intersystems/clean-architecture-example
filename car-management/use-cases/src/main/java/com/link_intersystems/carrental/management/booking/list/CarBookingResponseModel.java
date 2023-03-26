package com.link_intersystems.carrental.management.booking.list;

public class CarBookingResponseModel {
    private Integer bookingNumber;
    private String vin;
    private CustomerResponseModel customer;

    CarBookingResponseModel() {
    }

    public CustomerResponseModel getCustomer() {
        return customer;
    }

    void setCustomer(CustomerResponseModel customer) {
        this.customer = customer;
    }

    public String getVIN() {
        return vin;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    void setVIN(String VIN) {
        this.vin = VIN;
    }
}
