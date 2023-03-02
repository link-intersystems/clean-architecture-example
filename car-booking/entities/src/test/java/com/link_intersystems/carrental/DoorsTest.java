package com.link_intersystems.carrental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorsTest {

    @Test
    void wrongValues() {
        assertThrows(IllegalArgumentException.class, () -> new Doors(0));
    }
}