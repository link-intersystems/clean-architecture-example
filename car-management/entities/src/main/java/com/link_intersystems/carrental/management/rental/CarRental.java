package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.Period;

import java.time.LocalDateTime;

import static java.util.Objects.*;

public class CarRental {

    private BookingNumber bookingNumber;
    private Driver driver;
    private LocalDateTime pickupDateTime = ClockProvider.now();

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
        if (pickupDateTime.compareTo(returnDateTime) > 0) {
            throw new IllegalArgumentException("returnDate must be equal to or greater then the pickup date");
        }

        Odometer pickupOdometer = pickupCarState.getOdometer();
        if (pickupOdometer.compareTo(carState.getOdometer()) > 0) {
            throw new IllegalArgumentException("return odometer value must not be less then pickup value.");
        }

        this.returnDateTime = returnDateTime;
        returnCarState = carState;
    }

    public CarState getReturnCarState() {
        return returnCarState;
    }

    public Period getRentalPeriod() {
        if (returnDateTime == null) {
            return null;
        }

        return new Period(pickupDateTime, returnDateTime);
    }
}
