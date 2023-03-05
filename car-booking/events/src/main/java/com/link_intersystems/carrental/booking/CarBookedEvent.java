package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEvent;

public class CarBookedEvent extends DomainEvent {

    private Integer bookingNumber;
    private String vin;

    public CarBookedEvent(int bookingNumber, String vin) {
        this.bookingNumber = bookingNumber;
        this.vin = vin;
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
}
