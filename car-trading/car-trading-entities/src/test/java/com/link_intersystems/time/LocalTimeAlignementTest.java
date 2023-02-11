package com.link_intersystems.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalTimeAlignementTest {

    @Test
    public void align15Mins() {
        LocalTimeAlignement timeAlignement = new LocalTimeAlignement(15);

        assertEquals(timeOf(8, 0), timeAlignement.align(8, 0));
        assertEquals(timeOf(8, 0), timeAlignement.align(8, 7));
        assertEquals(timeOf(8, 15), timeAlignement.align(8, 8));
        assertEquals(timeOf(8, 15), timeAlignement.align(8, 14));
        assertEquals(timeOf(8, 15), timeAlignement.align(8, 15));
        assertEquals(timeOf(8, 30), timeAlignement.align(8, 33));
    }

    @Test
    public void align5Mins() {
        LocalTimeAlignement timeAlignement = new LocalTimeAlignement(5);

        assertEquals(timeOf(8, 0), timeAlignement.align(8, 2));
        assertEquals(timeOf(8, 5), timeAlignement.align(8, 3));
    }

    @Test
    public void align2Mins() {
        LocalTimeAlignement timeAlignement = new LocalTimeAlignement(2);

        assertEquals(timeOf(8, 0), timeAlignement.align(8, 0));
        assertEquals(timeOf(8, 2), timeAlignement.align(8, 1));
        assertEquals(timeOf(8, 2), timeAlignement.align(8, 2));
        assertEquals(timeOf(8, 4), timeAlignement.align(8, 3));
        assertEquals(timeOf(8, 4), timeAlignement.align(8, 4));
    }

    private LocalTime timeOf(int hour, int min) {
        return LocalTime.of(hour, min, 0);
    }


}