package com.link_intersystems.time;

import java.time.LocalTime;

public class LocalTimeAlignement {

    private final int halfIntervalMins;
    private int intervalMins;

    public LocalTimeAlignement(int intervalMins) {
        this.intervalMins = intervalMins;
        this.halfIntervalMins = (int) Math.round(intervalMins / 2.0);
    }

    public LocalTime align(int hour, int minute) {
        return align(LocalTime.of(hour, minute, 0));
    }

    public LocalTime align(LocalTime time) {
        int minute = time.getMinute();

        int intervalCount = 0;
        while (minute >= halfIntervalMins) {
            intervalCount++;
            minute = minute - intervalMins;
        }

        int alignedMin = intervalCount * intervalMins;

        return LocalTime.of(time.getHour(), alignedMin, 0);
    }
}
