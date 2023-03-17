package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.time.Period;

import java.time.LocalDateTime;

import static java.util.Objects.*;

public class CarRental {

    private BookingNumber bookingNumber;
    private Driver driver;
    private LocalDateTime pickupDateTime = LocalDateTime.now();
    private LocalDateTime returnDateTime;
    private CarState pickupCarState;
    private CarState returnCarState;

    public CarRental(BookingNumber bookingNumber, Driver driver, CarState pickupCarState) {
        this.bookingNumber = requireNonNull(bookingNumber);
        this.driver = requireNonNull(driver);
        this.pickupCarState = requireNonNull(pickupCarState);
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

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public CarState getPickupCarState() {
        return pickupCarState;
    }

    public void returnCar(CarState carState, LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
        returnCarState = carState;
    }

    public CarState getReturnCarState() {
        return returnCarState;
    }

    public Period getRentalPeriod() {
        return new Period(pickupDateTime, returnDateTime);
    }
}
