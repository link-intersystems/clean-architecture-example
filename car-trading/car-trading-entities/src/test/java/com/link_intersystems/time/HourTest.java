package com.link_intersystems.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HourTest {

    @Test
    void getValue() {
        assertEquals(8, Hour.of(8).getValue());
    }

    @Test
    void wrongHourValues() {
        assertThrows(HourOutOfBoundsException.class, () -> Hour.of(-1));
        assertThrows(HourOutOfBoundsException.class, () -> Hour.of(24));
    }
}