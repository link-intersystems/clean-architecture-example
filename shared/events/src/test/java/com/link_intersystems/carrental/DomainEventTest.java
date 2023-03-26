package com.link_intersystems.carrental;

import com.link_intersystems.carrental.time.FixedClock;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {

    @Test
    @FixedClock("2023-03-26 10:46:23")
    void getOccuredOn() {
        DomainEvent domainEvent = new DomainEvent() {
        };

        LocalDateTime expectedOccuredOn = LocalDateTime.of(2023, 3, 26, 10, 46, 23);
        assertEquals(expectedOccuredOn, domainEvent.getOccuredOn());
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(DomainEvent.class).verify();

    }
}