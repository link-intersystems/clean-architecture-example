package com.link_intersystems.carrental.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class PeriodTest {

    @Test
    void beginAfterEnd() {
        assertThrows(IllegalArgumentException.class, () -> PeriodBuilder.from("2023-01-15", "09:00:00").to("2023-01-15", "08:00:00"));
    }

    @Test
    void endBeforeBegin() {
        assertThrows(IllegalArgumentException.class, () -> PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-15", "07:00:00"));
    }

    @Test
    void getDays() {
        Assertions.assertEquals(1, PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-15", "17:00:00").getDays());
        Assertions.assertEquals(3, PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "08:00:00").getDays());
    }

    /**
     * <pre>
     * period1    |------------------|
     * period2             |---------------|
     * </pre>
     */
    @Test
    void pickupDateOverlaps() {
        Period period1 = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        Period period2 = PeriodBuilder.from("2023-01-15", "09:00:00").to("2023-01-18", "17:00:00");

        assertTrue(period1.overlaps(period2));
        assertTrue(period2.overlaps(period1));
    }

    /**
     * <pre>
     * period1    |------------------|
     * period2             |-----|
     * </pre>
     */
    @Test
    void pickupAndReturnDateDateOverlaps() {
        Period period1 = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        Period period2 = PeriodBuilder.from("2023-01-15", "09:00:00").to("2023-01-17", "16:00:00");

        assertTrue(period1.overlaps(period2));
        assertTrue(period2.overlaps(period1));
    }

    /**
     * <pre>
     * period1    |------------------|
     * period2             |-----|
     * </pre>
     */
    @Test
    void onlyReturnDateOverlaps() {
        Period period1 = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        Period period2 = PeriodBuilder.from("2023-01-14", "08:00:00").to("2023-01-15", "09:00:00");

        assertTrue(period1.overlaps(period2));
        assertTrue(period2.overlaps(period1));
    }

    /**
     * <pre>
     * period1    |-------|
     * period2            |-----|
     * </pre>
     */
    @Test
    void overlapsByEndDateTime() {
        Period period1 = PeriodBuilder.from("2023-01-13", "08:00:00").to("2023-01-15", "08:00:00");
        Period period2 = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-15", "09:00:00");

        assertTrue(period1.overlaps(period2));
        assertTrue(period2.overlaps(period1));
    }

    /**
     * <pre>
     * period1           |-------|
     * period2     |-----|
     * </pre>
     */
    @Test
    void overlapsByBeginDateTime() {
        Period period1 = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-15", "09:00:00");
        Period period2 = PeriodBuilder.from("2023-01-13", "08:00:00").to("2023-01-15", "08:00:00");

        assertTrue(period1.overlaps(period2));
        assertTrue(period2.overlaps(period1));
    }

    /**
     * <pre>
     * period1            |-----|
     * period2  |-----|
     * </pre>
     */
    @Test
    void nonOverlappingBefore() {
        Period period1 = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        Period period2 = PeriodBuilder.from("2023-01-14", "08:00:00").to("2023-01-15", "07:00:00");

        assertFalse(period1.overlaps(period2));
        assertFalse(period2.overlaps(period1));
    }

    @Test
    void toStringTest() {
        Period period = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        assertNotNull(period.toString());
    }

    @Test
    @FixedClock("2023-01-20 08:00:00")
    void past(Clock clock) {
        Period period = PeriodBuilder.from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        assertTrue(period.isPast(clock));
    }

}