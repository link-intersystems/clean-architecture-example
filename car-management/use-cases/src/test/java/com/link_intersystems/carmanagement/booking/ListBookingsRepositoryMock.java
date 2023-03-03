package com.link_intersystems.carmanagement.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListBookingsRepositoryMock implements ListBookingsRepository {
    @Override
    public List<CarBooking> findBookings(LocalDateTime from, LocalDateTime to) {
        ArrayList<CarBooking> carBookings = new ArrayList<>();
        carBookings.add(new CarBooking());
        carBookings.add(new CarBooking());
        return carBookings;
    }
}
