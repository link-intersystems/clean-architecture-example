package com.link_intersystems.time;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class PickupTimeTest {

    @Test
    void newInstance() {
        PickupTime pickupTime = new PickupTime(Hour.of(8), PickupMinute.THIRTY);
        assertEquals(LocalTime.of(8, 30, 0), pickupTime.getTime());
    }
}