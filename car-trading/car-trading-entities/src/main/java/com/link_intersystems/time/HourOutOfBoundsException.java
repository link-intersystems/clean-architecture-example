package com.link_intersystems.time;

public class HourOutOfBoundsException extends RuntimeException {
    private int hour;

    public HourOutOfBoundsException(int hour) {
        this.hour = hour;
    }

    @Override
    public String getMessage() {
        return "Hour " + hour + " is out of bounds 0 - 23";
    }
}
