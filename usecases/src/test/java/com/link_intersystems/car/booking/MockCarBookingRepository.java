package com.link_intersystems.car.booking;

import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.person.customer.CustomerFixture;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

class MockCarBookingRepository implements CarBookingRepository {

    private final CarFixture carFixture;
    private final CustomerFixture customers;

    private int bookingNumberSeq = 1;
    private CarBooking latestPersistedCarBooking;

    public MockCarBookingRepository(CarFixture carFixture, CustomerFixture customers) {

        this.carFixture = carFixture;
        this.customers = customers;
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        return null;
    }

    @Override
    public void persist(CarBooking carBooking) {
        BookingNumber bookingNumber = nextBookingNumber();
        carBooking.setBookingNumber(bookingNumber);
        this.latestPersistedCarBooking = carBooking;
    }

    private BookingNumber nextBookingNumber() {
        return new BookingNumber(bookingNumberSeq++);
    }

    @Override
    public boolean isCustomerExistent(CustomerId customerId) {
        return customers.getById(customerId.getValue()) != null;
    }

    public CarBooking getLastPersistedCarBooking() {
        return latestPersistedCarBooking;
    }
}
