package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBookingTest {

    private CarBookingFixture carBookingFixture;

    @BeforeEach
    void setUp() {
        carBookingFixture = new CarBookingFixture();
    }

    @Test
    void rentalState() {
        BookingNumber bookingNumber = new BookingNumber(42);
        VIN vin = new VIN("WMEEJ8AA3FK792135");
        Customer customer = new Customer("René", "Link");
        CarBooking carBooking = new CarBooking(bookingNumber, vin, customer);

        assertNull(carBooking.getRentalState());

        carBooking.setRentalState(RentalState.PICKED_UP);

        assertEquals(RentalState.PICKED_UP, carBooking.getRentalState());
    }

    @Test
    void equalsTest() {
        CarBooking reneLinkBooking1 = carBookingFixture.getReneLinkBooking();
        CarBooking reneLinkBooking2 = carBookingFixture.getReneLinkBooking();

        assertEquals(reneLinkBooking1, reneLinkBooking2);
        assertEquals(reneLinkBooking1.hashCode(), reneLinkBooking2.hashCode());
    }

}