package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.carrental.management.booking.CarBooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListBookingsRepositoryMock implements ListBookingsRepository {
    @Override
    public List<CarBooking> findBookings(LocalDateTime from, LocalDateTime to) {
        ArrayList<CarBooking> carBookings = new ArrayList<>();
        carBookings.add(new CarBooking(1, "WMEEJ8AA3FK792135"));
        carBookings.add(new CarBooking(2, "WMEEJ8AA3FK792135"));
        return carBookings;
    }
}
