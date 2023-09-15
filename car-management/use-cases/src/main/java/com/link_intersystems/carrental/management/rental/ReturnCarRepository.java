package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;

interface ReturnCarRepository {
    CarRental find(BookingNumber bookingNumber);

    void update(CarRental carRental);
}
