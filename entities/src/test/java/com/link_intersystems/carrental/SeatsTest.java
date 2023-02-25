package com.link_intersystems.carrental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatsTest {

    @Test
    void wrongValues() {
        assertThrows(IllegalArgumentException.class, () -> new Seats(0));
        assertThrows(IllegalArgumentException.class, () -> new Seats(9));
    }
}