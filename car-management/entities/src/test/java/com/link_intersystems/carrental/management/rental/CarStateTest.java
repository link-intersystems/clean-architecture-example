package com.link_intersystems.carrental.management.rental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarStateTest {

    @Test
    void equalsAndHashCode() {
        CarState carState1 = new CarState(FuelLevel.HALF, Odometer.of(1234));
        CarState carState2 = new CarState(FuelLevel.HALF, Odometer.of(1234));

        assertEquals(carState1, carState2);
        assertEquals(carState1.hashCode(), carState2.hashCode());

        CarState carState3 = new CarState(FuelLevel.HALF, Odometer.of(12345));
        assertNotEquals(carState1, carState3);
    }
}
