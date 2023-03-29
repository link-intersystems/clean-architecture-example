package com.link_intersystems.carrental.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIdTest {

    @Test
    void wrongIdValue() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerId(-1));
    }

    @Test
    void verifyEqualsAndHashCode() {
        assertEquals(new CustomerId(1), new CustomerId(1));
        assertEquals(new CustomerId(1).hashCode(), new CustomerId(1).hashCode());

        assertNotEquals(new CustomerId(1), new CustomerId(2));
        assertNotEquals(new CustomerId(1).hashCode(), new CustomerId(2).hashCode());
    }
}