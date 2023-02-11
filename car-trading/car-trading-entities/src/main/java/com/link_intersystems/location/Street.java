package com.link_intersystems.location;

public class Street {

    private String name;
    private String number;

    public Street(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
