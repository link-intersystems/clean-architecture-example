package com.link_intersystems.carmanagement.booking.list;

import com.link_intersystems.carmanagement.booking.CarBooking;

import java.time.LocalDateTime;
import java.util.List;

interface ListBookingsRepository {
    List<CarBooking> findBookings(LocalDateTime from, LocalDateTime to);
}
