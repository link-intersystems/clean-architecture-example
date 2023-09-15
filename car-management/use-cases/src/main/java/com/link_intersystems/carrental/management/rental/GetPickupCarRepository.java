package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;

interface GetPickupCarRepository {
    CarRental find(BookingNumber bookingNumber);
}
