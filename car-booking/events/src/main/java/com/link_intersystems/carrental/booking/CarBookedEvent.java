package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEvent;

public class CarBookedEvent extends DomainEvent {

    private Integer bookingNumber;
    private String vin;
    private String customerFirstname;
    private String customerLastname;

    public CarBookedEvent(int bookingNumber, String vin, String customerFirstname, String customerLastname) {
        this.bookingNumber = bookingNumber;
        this.vin = vin;
        this.customerFirstname = customerFirstname;
        this.customerLastname = customerLastname;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public String getVin() {
        return vin;
    }

    @Override
    public String toString() {
        return "CarBookedEvent{" +
                "bookingNumber=" + bookingNumber +
                ", vin='" + vin + '\'' +
                "} " + super.toString();
    }

    public String getCustomerLastname() {
        return customerLastname;
    }
}
