package com.link_intersystems.carrental;

import com.link_intersystems.carrental.time.FixedClock;
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
    @FixedClock("2023-03-26 10:46:23")
    void testEquals() {
        class TestDomainEvent extends DomainEvent {
        }
        DomainEvent domainEvent1 = new TestDomainEvent();
        DomainEvent domainEvent2 = new TestDomainEvent();
        assertEquals(domainEvent1, domainEvent2);
        assertEquals(domainEvent1.hashCode(), domainEvent2.hashCode());
    }
}