package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;

public class PickupCarRepositoryMock implements PickupCarRepository {
    private CarRental carRental;
    private CarBooking carBooking;

    public CarBooking getCarBooking() {
        return carBooking;
    }

    public void setCarBooking(CarBooking carBooking) {
        this.carBooking = carBooking;
    }

    @Override
    public void persist(CarRental carPickup) {
        this.carRental = carPickup;
    }

    @Override
    public CarBooking findBooking(BookingNumber bookingNumber) {
        return carBooking;
    }

    public CarRental getCarRental() {
        return carRental;
    }
}
