package com.link_intersystems.carrental.booking;

import com.link_intersystems.time.Period;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class CarBookings extends AbstractList<CarBooking> {

    private List<CarBooking> carBookings = new ArrayList<>();

    public CarBookings() {
    }

    public CarBookings(List<CarBooking> carBookings) {
        this.carBookings.addAll(carBookings);
    }

    @Override
    public CarBooking get(int index) {
        return carBookings.get(index);
    }

    @Override
    public int size() {
        return carBookings.size();
    }

    public boolean isAvailable(Period period) {
        for (CarBooking carRental : this) {
            Period bookingPeriod = carRental.getBookingPeriod();
            if (bookingPeriod.overlaps(period)) {
                return false;
            }
        }
        return true;
    }
}
