package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;

import java.util.ArrayList;
import java.util.List;

public class ListBookingsRepositoryMock implements ListBookingsRepository {
    @Override
    public List<CarBooking> findBookings() {
        ArrayList<CarBooking> carBookings = new ArrayList<>();
        carBookings.add(new CarBooking(new BookingNumber(1), new VIN("WMEEJ8AA3FK792135"), new Customer("Nick", "Wahlberg")));
        carBookings.add(new CarBooking(new BookingNumber(2), new VIN("WMEEJ8AA3FK792135"), new Customer("René", "Link")));
        return carBookings;
    }
}
