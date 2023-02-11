package com.link_intersystems.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarIdTest {

    @Test
    void testEquals() {
        assertEquals(new CarId(1), new CarId(1));
    }

    @Test
    void testHashCode() {
        assertEquals(new CarId(1).hashCode(), new CarId(1).hashCode());
    }

    @Test
    void getValue() {
        assertEquals(1, new CarId(1).getValue());
    }

    @Test
    void testToString() {
        new CarId(1).toString();
    }
}