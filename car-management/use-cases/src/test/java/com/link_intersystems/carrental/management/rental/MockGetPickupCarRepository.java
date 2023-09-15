package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;

import java.util.HashMap;
import java.util.Map;

public class MockGetPickupCarRepository implements GetPickupCarRepository {

    private Map<Integer, CarRental> carRentalMap = new HashMap<>();

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        return carRentalMap.get(bookingNumber.getValue());
    }

    public void putCarRental(int bookingNumber, CarRental carRental) {
        carRentalMap.put(bookingNumber, carRental);
    }
}
