package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.booking.CarBooking;

import java.util.List;

interface ListBookingsRepository {
    List<CarBooking> findBookings();
}
