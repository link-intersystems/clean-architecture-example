package com.link_intersystems.carmanagement.booking.create;

import com.link_intersystems.carmanagement.booking.CarBooking;

interface CreateCarBookingRepository {
    void persist(CarBooking carBooking);
}
