package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;

public interface UpdateCarBookingRentalRepository {
    CarBooking findCarBooking(BookingNumber bookingNumber);

    void persist(CarBooking carBooking);
}
