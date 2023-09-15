package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;

interface PickupCarRepository {
    void persist(CarRental carRental);

    CarBooking findBooking(BookingNumber bookingNumber);
}
