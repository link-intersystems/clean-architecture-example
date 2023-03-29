package com.link_intersystems.carrental.management.rental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OdometerTest {

    @Test
    void of() {
        Odometer odometer = Odometer.of(12345);

        assertEquals(12345, odometer.getValue());
    }

    @Test
    void ofIllegalValue() {
        assertThrows(IllegalArgumentException.class, () -> Odometer.of(-1));
    }

    @Test
    void compareTo() {
        assertEquals(-1, Odometer.of(1).compareTo(Odometer.of(2)));
        assertEquals(0, Odometer.of(2).compareTo(Odometer.of(2)));
        assertEquals(1, Odometer.of(3).compareTo(Odometer.of(2)));
    }

    @Test
    void testEquals() {
        assertEquals(Odometer.of(1), Odometer.of(1));
        assertEquals(Odometer.of(1).hashCode(), Odometer.of(1).hashCode());

        assertNotEquals(Odometer.of(1), Odometer.of(2));
        assertNotEquals(Odometer.of(1).hashCode(), Odometer.of(2).hashCode());
    }
}