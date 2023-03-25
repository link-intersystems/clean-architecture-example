package com.link_intersystems.carrental.management.rental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuelLevelTest {

    @Test
    void ofIllegalPercentage() {
        assertThrows(IllegalArgumentException.class, () -> FuelLevel.ofPercentage(-1));
        assertThrows(IllegalArgumentException.class, () -> FuelLevel.ofPercentage(101));
    }

    @Test
    void ofPercentage() {
        assertEquals(FuelLevel.EMPTY, FuelLevel.ofPercentage(0));
        assertEquals(FuelLevel.EMPTY, FuelLevel.ofPercentage(12));

        assertEquals(FuelLevel.ONE_QUARTER, FuelLevel.ofPercentage(13));
        assertEquals(FuelLevel.ONE_QUARTER, FuelLevel.ofPercentage(37));

        assertEquals(FuelLevel.HALF, FuelLevel.ofPercentage(38));
        assertEquals(FuelLevel.HALF, FuelLevel.ofPercentage(62));

        assertEquals(FuelLevel.THREE_QUARTER, FuelLevel.ofPercentage(63));
        assertEquals(FuelLevel.THREE_QUARTER, FuelLevel.ofPercentage(87));

        assertEquals(FuelLevel.FULL, FuelLevel.ofPercentage(88));
        assertEquals(FuelLevel.FULL, FuelLevel.ofPercentage(100));
    }
}