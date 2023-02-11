package com.link_intersystems.location.station;

import com.link_intersystems.location.Address;

import java.time.ZoneId;

public class Station {

    private int id;
    private String name;
    private Address address;

    private ZoneId zoneId = ZoneId.systemDefault();

    public Station(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
