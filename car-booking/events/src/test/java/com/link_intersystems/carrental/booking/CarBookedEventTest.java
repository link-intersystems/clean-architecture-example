package com.link_intersystems.carrental.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBookedEventTest {

    @Test
    void properties() {
        CarBookedEvent carBookedEvent = new CarBookedEvent(1, "WMEEJ8AA3FK792135");

        assertEquals(1, carBookedEvent.getBookingNumber());
        assertEquals("WMEEJ8AA3FK792135", carBookedEvent.getVin());
    }

    @Test
    void testToString() {
        CarBookedEvent carBookedEvent = new CarBookedEvent(1, "WMEEJ8AA3FK792135");
        assertNotNull(carBookedEvent.toString());
    }
}
