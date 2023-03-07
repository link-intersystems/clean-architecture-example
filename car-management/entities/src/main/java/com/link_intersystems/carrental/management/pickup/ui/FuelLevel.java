package com.link_intersystems.carrental.management.pickup.ui;

public enum FuelLevel {
    EMPTY, ONE_QUARTER, HALF, THREE_QUARTER, FULL;

    public static FuelLevel ofPercentage(int percentage) {
        if (percentage >= 0 && percentage <= 12) {
            return EMPTY;
        } else if (percentage >= 13 && percentage <= 37) {
            return ONE_QUARTER;
        } else if (percentage >= 38 && percentage <= 62) {
            return HALF;
        } else if (percentage >= 63 && percentage <= 87) {
            return THREE_QUARTER;
        } else if (percentage >= 88 && percentage <= 100) {
            return FULL;
        }
        throw new IllegalArgumentException("Not a percentage value between 0 - 100: " + percentage);
    }
}
