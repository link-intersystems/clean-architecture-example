package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;

interface GetPickupCarRepository {
    CarRental find(BookingNumber bookingNumber);
}
