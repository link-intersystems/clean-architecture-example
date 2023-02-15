package com.link_intersystems.location.station;

import com.link_intersystems.location.Address;

import java.time.ZoneId;

public class Station {

    private StationId id;
    private String name;
    private Address address;

    private ZoneId zoneId = ZoneId.systemDefault();

    public Station(StationId id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public StationId getId() {
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
