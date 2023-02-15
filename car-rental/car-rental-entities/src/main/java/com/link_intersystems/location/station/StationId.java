package com.link_intersystems.location.station;

public class StationId {

    private int value;

    public StationId(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be 0 or greater.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
