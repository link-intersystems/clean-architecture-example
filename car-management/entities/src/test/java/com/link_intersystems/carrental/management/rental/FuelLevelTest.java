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

    @Test
    void percent() {
        assertEquals(0, FuelLevel.EMPTY.getPercent());
        assertEquals(25, FuelLevel.ONE_QUARTER.getPercent());
        assertEquals(50, FuelLevel.HALF.getPercent());
        assertEquals(75, FuelLevel.THREE_QUARTER.getPercent());
        assertEquals(100, FuelLevel.FULL.getPercent());
    }
}