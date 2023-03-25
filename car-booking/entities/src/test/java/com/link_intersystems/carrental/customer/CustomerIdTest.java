package com.link_intersystems.carrental.customer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIdTest {

    @Test
    void wrongIdValue() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerId(-1));
    }

    @Test
    void verifyEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(CustomerId.class).verify();
    }
}