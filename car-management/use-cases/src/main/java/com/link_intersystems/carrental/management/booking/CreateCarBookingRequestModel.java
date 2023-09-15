package com.link_intersystems.carrental.management.booking;

public class CreateCarBookingRequestModel {
    private Integer bookingNumber;
    private String vin;
    private CustomerRequestModel customerRequestModel;

    CustomerRequestModel getCustomerRequestModel() {
        return customerRequestModel;
    }

    public void setCustomerRequestModel(CustomerRequestModel customerRequestModel) {
        this.customerRequestModel = customerRequestModel;
    }

    Integer getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(Integer bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    String getVIN() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
