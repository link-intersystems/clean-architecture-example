package com.link_intersystems.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarIdTest {

    @Test
    void testEquals() {
        assertEquals(newCarId("YV4952ND0F1201834"), newCarId("YV4952ND0F1201834"));
    }

    private static CarId newCarId(String value) {
        return new CarId(new VIN(value));
    }

    @Test
    void testHashCode() {
        assertEquals(newCarId("YV4952ND0F1201834").hashCode(), newCarId("YV4952ND0F1201834").hashCode());
    }

    @Test
    void getValue() {
        assertEquals("YV4952ND0F1201834", newCarId("YV4952ND0F1201834").getValue());
    }

    @Test
    void testToString() {
        newCarId("YV4952ND0F1201834").toString();
    }
}