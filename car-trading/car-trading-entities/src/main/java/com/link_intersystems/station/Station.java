package com.link_intersystems.station;

import com.link_intersystems.location.Address;

public class Station {

    private int id;
    private String name;
    private Address address;

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
}
