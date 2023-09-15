package com.link_intersystems.carrental.management.booking;

import java.util.List;

interface ListBookingsRepository {
    List<CarBooking> findBookings();
}
