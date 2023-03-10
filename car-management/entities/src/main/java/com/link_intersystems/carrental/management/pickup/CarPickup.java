package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.booking.BookingNumber;

import java.time.LocalDateTime;

import static java.util.Objects.*;

public class CarPickup {

    private BookingNumber bookingNumber;
    private Driver driver;
    private LocalDateTime pickupDateTime = LocalDateTime.now();
    private CarState carState;

    public CarPickup(BookingNumber bookingNumber, Driver driver, CarState carState) {
        this.bookingNumber = requireNonNull(bookingNumber);
        this.driver = requireNonNull(driver);
        this.carState = requireNonNull(carState);
    }

    public BookingNumber getBookingNumber() {
        return bookingNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = requireNonNull(pickupDateTime);
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public CarState getCarState() {
        return carState;
    }
}
