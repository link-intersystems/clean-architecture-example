package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.rental.CarRental;

public class PickupCarRepositoryMock implements PickupCarRepository {
    private CarRental carRental;
    private CarBooking carBooking;

    public void setCarBooking(CarBooking carBooking) {
        this.carBooking = carBooking;
    }

    public CarBooking getCarBooking() {
        return carBooking;
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
