package com.link_intersystems.location;

public class Address {

    private Street street;
    private String zipCode;
    private String city;

    public Address(Street street, String zipCode, String city) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Street getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
}
