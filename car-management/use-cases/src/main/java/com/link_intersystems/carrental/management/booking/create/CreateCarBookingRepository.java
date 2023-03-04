package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.management.booking.CarBooking;

interface CreateCarBookingRepository {
    void persist(CarBooking carBooking);
}
