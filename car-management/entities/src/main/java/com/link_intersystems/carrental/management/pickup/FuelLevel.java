package com.link_intersystems.carrental.management.pickup;

public enum FuelLevel {
    EMPTY(0), ONE_QUARTER(25), HALF(50), THREE_QUARTER(75), FULL(100);

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

    private int percent;

    FuelLevel(int percent) {
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }
}
