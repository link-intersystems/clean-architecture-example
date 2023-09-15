package com.link_intersystems.carrental.management.booking;

class MockCreateCarBookingRepository implements CreateCarBookingRepository {
    private CarBooking carBooking;

    @Override
    public void persist(CarBooking carBooking) {
        this.carBooking = carBooking;
    }

    CarBooking getLatestPersistedCarBooking() {
        return carBooking;
    }
}
