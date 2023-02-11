package com.link_intersystems.time;

public class Hour {

    private int value;

    public static final Hour of(int hour){
        if(hour < 0 || hour > 23){
            throw new HourOutOfBoundsException(hour);
        }

        return new Hour(hour);
    }

    private Hour(int hour) {
        value = hour;
    }

    public int getValue() {
        return value;
    }
}
