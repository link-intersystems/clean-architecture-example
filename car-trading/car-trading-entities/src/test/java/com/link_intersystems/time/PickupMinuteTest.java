package com.link_intersystems.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PickupMinuteTest {

    @Test
    void closestPickupMinute0() {
        PickupMinute pickupMinute = PickupMinute.closestPickupMinute(LocalTime.of(8, 14, 4));

        assertEquals(PickupMinute.ZERO, pickupMinute);
    }

    @Test
    void closestPickupMinute30() {
        PickupMinute pickupMinute = PickupMinute.closestPickupMinute(LocalTime.of(8, 15, 4));

        assertEquals(PickupMinute.THIRTY, pickupMinute);
    }
}