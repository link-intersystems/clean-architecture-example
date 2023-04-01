package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.carrental.time.PeriodBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class CarBookingTest {

    private CarBookingFixture carBookingFixture;

    @BeforeEach
    void setUp() {
        carBookingFixture = new CarBookingFixture(new CarFixture());
    }

    @Test
    void bookingNumber() {
        CarBooking booking1 = carBookingFixture.getBooking1();
        booking1.setBookingNumber(new BookingNumber(123));

        assertEquals(new BookingNumber(123), booking1.getBookingNumber());
    }

    @Test
    void customerId() {
        CarBooking booking1 = carBookingFixture.getBooking1();

        assertEquals(new CustomerId(1), booking1.getCustomerId());
    }

    @Test
    void carId() {
        CarId carId = carBookingFixture.getBooking1().getCarId();

        CarId expectedCarId = new CarId(new VIN("WMEEJ8AA3FK792135"));
        assertEquals(expectedCarId, carId);
    }

    @Test
    void bookingPeriod() {
        CarBooking booking1 = carBookingFixture.getBooking1();
        Period bookingPeriod = booking1.getBookingPeriod();

        Period expectedPeriod = from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        assertEquals(expectedPeriod, bookingPeriod);
    }
}