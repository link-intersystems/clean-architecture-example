package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.rental.CarRental;

interface PickupCarRepository {
    void persist(CarRental carRental);

    CarBooking findBooking(BookingNumber bookingNumber);
}
