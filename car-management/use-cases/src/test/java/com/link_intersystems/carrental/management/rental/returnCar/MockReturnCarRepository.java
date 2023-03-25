package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;

import java.util.HashMap;
import java.util.Map;

public class MockReturnCarRepository implements ReturnCarRepository {
    private CarRental carRental;
    private Map<Integer, CarRental> carRentalMap = new HashMap<>();

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        return carRentalMap.get(bookingNumber.getValue());
    }

    @Override
    public void update(CarRental carRental) {
        this.carRental = carRental;
    }

    CarRental getLatestPersistedCarRental() {
        return carRental;
    }

    void addCarRental(CarRental carRental) {
        carRentalMap.put(carRental.getBookingNumber().getValue(), carRental);
    }
}
