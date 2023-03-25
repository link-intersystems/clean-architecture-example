package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;

import java.util.*;

public class CarBookinsByCar extends AbstractMap<CarId, List<CarBooking>> {

    private Map<CarId, List<CarBooking>> bookingsByCar = new HashMap<>();

    public CarBookinsByCar(List<CarBooking> carBookings) {
        for (CarBooking carBooking : carBookings) {
            CarId carId = carBooking.getCarId();
            List<CarBooking> carBookingById = bookingsByCar.computeIfAbsent(carId, id -> new ArrayList<>());
            carBookingById.add(carBooking);
        }
    }

    @Override
    public Set<Entry<CarId, List<CarBooking>>> entrySet() {
        return Collections.unmodifiableMap(bookingsByCar).entrySet();
    }

    public boolean contains(CarId carId) {
        return keySet().contains(carId);
    }
}
