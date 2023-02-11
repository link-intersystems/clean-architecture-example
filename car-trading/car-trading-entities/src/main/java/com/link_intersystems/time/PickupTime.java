package com.link_intersystems.time;

import java.time.LocalTime;

public class PickupTime {

    private Hour hour;
    private PickupMinute pickupMinute;

    public PickupTime(Hour hour, PickupMinute pickupMinute){
        this.hour = hour;
        this.pickupMinute = pickupMinute;
    }

    public LocalTime getTime() {
        return LocalTime.of(hour.getValue(), pickupMinute.getValue());
    }
}
