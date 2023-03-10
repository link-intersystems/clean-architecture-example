package com.link_intersystems.carrental.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingNumberTest {

    @Test
    void getValue() {
        BookingNumber bookingNumber = new BookingNumber(1);
        assertEquals(1, bookingNumber.getValue());

        assertThrows(IllegalArgumentException.class, () -> new BookingNumber(0));

    }

    @Test
    void testToString() {
        BookingNumber bookingNumber = new BookingNumber(1);
        assertNotNull(bookingNumber.toString());
    }
}