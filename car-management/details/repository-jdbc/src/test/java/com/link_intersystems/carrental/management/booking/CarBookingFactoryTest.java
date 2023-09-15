package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarBookingFactoryTest {

    private Map<String, Object> row;
    private CarBookingFactory mapper;

    @BeforeEach
    void setUp() {
        row = new HashMap<>();
        mapper = new CarBookingFactory();
    }


    @Test
    void toCarBooking() {
        row.put("BOOKING_NUMBER", 42);
        row.put("VIN", "WMEEJ8AA3FK792135");
        row.put("CUSTOMER_FIRSTNAME", "René");
        row.put("CUSTOMER_LASTNAME", "Link");

        CarBooking carBooking = mapper.create(row);

        assertEquals(new BookingNumber(42), carBooking.getBookingNumber());
        assertEquals(new VIN("WMEEJ8AA3FK792135"), carBooking.getVin());
        Customer customer = carBooking.getCustomer();
        assertEquals("René", customer.getFirstname());
        assertEquals("Link", customer.getLastname());
    }
}