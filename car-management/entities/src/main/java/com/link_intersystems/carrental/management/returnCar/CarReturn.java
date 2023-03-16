package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.pickup.CarPickup;
import com.link_intersystems.carrental.management.pickup.CarState;
import com.link_intersystems.carrental.time.Period;

import java.time.LocalDateTime;

import static java.util.Objects.*;

public class CarReturn {

    private BookingNumber bookingNumber;
    private LocalDateTime returnDateTime = LocalDateTime.now();
    private CarState carState;

    public CarReturn(BookingNumber bookingNumber, CarState carState) {
        this.bookingNumber = requireNonNull(bookingNumber);
        this.carState = requireNonNull(carState);
    }

    public BookingNumber getBookingNumber() {
        return bookingNumber;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = requireNonNull(returnDateTime);
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public CarState getCarState() {
        return carState;
    }

    public Period getRentalPeriod(CarPickup carPickup) {
        LocalDateTime pickupDateTime = carPickup.getPickupDateTime();
        return new Period(pickupDateTime, returnDateTime);
    }
}
