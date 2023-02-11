package com.link_intersystems.rental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalPeriodTest {

    /**
     * <pre>
     * period1    |------------------|
     * period2             |---------------|
     * </pre>
     */
    @Test
    void pickUpDateOverlaps() {
        RentalPeriod period1 = new RentalPeriod(dateTime(15, 8), dateTime(17, 17));
        RentalPeriod period2 = new RentalPeriod(dateTime(15, 9), dateTime(18, 17));

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
    void pickUpAndReturnDateDateOverlaps() {
        RentalPeriod period1 = new RentalPeriod(dateTime(15, 8), dateTime(17, 17));
        RentalPeriod period2 = new RentalPeriod(dateTime(15, 9), dateTime(17, 16));

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
        RentalPeriod period1 = new RentalPeriod(dateTime(15, 8), dateTime(17, 17));
        RentalPeriod period2 = new RentalPeriod(dateTime(14, 8), dateTime(15, 9));

        assertTrue(period1.overlaps(period2));
        assertTrue(period2.overlaps(period1));
    }

    /**
     * <pre>
     * period1         |-----|
     * period2  |-----|
     * </pre>
     */
    @Test
    void nonOverlappingBefore() {
        RentalPeriod period1 = new RentalPeriod(dateTime(15, 8), dateTime(17, 17));
        RentalPeriod period2 = new RentalPeriod(dateTime(14, 8), dateTime(15, 7));

        assertFalse(period1.overlaps(period2));
        assertFalse(period2.overlaps(period1));
    }

    private static LocalDateTime dateTime(int dayOfMonth, int hour) {
        return LocalDateTime.of(2023, 1, dayOfMonth, hour, 0, 0);
    }
}