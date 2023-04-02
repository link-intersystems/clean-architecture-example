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
    void equalsAndHashCode() {
        BookingNumber bookingNumber1 = new BookingNumber(1);
        BookingNumber bookingNumber2 = new BookingNumber(1);

        assertEquals(bookingNumber1, bookingNumber2);
        assertEquals(bookingNumber1.hashCode(), bookingNumber2.hashCode());

        BookingNumber bookingNumber3 = new BookingNumber(2);
        assertNotEquals(bookingNumber1, bookingNumber3);
    }

    @Test
    void testToString() {
        BookingNumber bookingNumber = new BookingNumber(1);
        assertNotNull(bookingNumber.toString());
    }
}