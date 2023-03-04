package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEvent;

import java.time.LocalDateTime;

public class CarBookedEvent implements DomainEvent {

    private LocalDateTime occuredOn = LocalDateTime.now();
    private Integer bookingNumber;
    private String vin;

    public CarBookedEvent(int bookingNumber, String vin) {
        this.bookingNumber = bookingNumber;
        this.vin = vin;
    }

    @Override
    public LocalDateTime occuredOn() {
        return occuredOn;
    }

    @Override
    public String toString() {
        return "CarBookedEvent{" +
                "occuredOn=" + occuredOn +
                '}';
    }

    public Integer getBookingNumber() {
        return bookingNumber;
    }

    public String getVin() {
        return vin;
    }

}
