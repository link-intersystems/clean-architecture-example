package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerFixture;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;

import java.util.Optional;

class MockCarBookingRepository implements CarBookingRepository {

    private final CarFixture carFixture;
    private final CustomerFixture customers;
    private CarBookingFixture carBookingFixture;

    private int bookingNumberSeq = 1;
    private CarBooking latestPersistedCarBooking;

    public MockCarBookingRepository(CarFixture carFixture, CustomerFixture customers, CarBookingFixture carBookingFixture) {

        this.carFixture = carFixture;
        this.customers = customers;
        this.carBookingFixture = carBookingFixture;
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        Optional<CarBooking> carBooking = carBookingFixture.stream() //
                .filter(cb -> cb.getCarId().equals(carId)) //
                .filter(cb -> cb.getBookingPeriod().overlaps(bookingPeriod)) //
                .findFirst();
        return carBooking.orElse(null);
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
    public Customer findCustomer(CustomerId customerId) {
        return customers.getById(customerId.getValue());
    }

    public CarBooking getLastPersistedCarBooking() {
        return latestPersistedCarBooking;
    }
}
