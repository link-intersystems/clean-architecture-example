package com.link_intersystems.carrental.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBookedEventTest {

    @Test
    void properties() {
        CarBookedEvent carBookedEvent = new CarBookedEvent(1, "WMEEJ8AA3FK792135", "René", "Link");

        assertEquals(1, carBookedEvent.getBookingNumber());
        assertEquals("WMEEJ8AA3FK792135", carBookedEvent.getVin());
        assertEquals("René", carBookedEvent.getCustomerFirstname());
        assertEquals("Link", carBookedEvent.getCustomerLastname());
    }

    @Test
    void testToString() {
        CarBookedEvent carBookedEvent = new CarBookedEvent(1, "WMEEJ8AA3FK792135", "René", "Link");
        assertNotNull(carBookedEvent.toString());
    }
}
