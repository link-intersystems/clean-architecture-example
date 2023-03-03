package com.link_intersystems.carmanagement.booking;

import com.link_intersystems.carmanagement.booking.CarBooking;

import java.time.LocalDateTime;
import java.util.List;

public interface ListBookingsRepository {
    List<CarBooking> findBookings(LocalDateTime from, LocalDateTime to);
}
