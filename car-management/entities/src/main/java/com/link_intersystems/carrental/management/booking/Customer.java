package com.link_intersystems.carrental.management.booking;

import java.util.Objects;

public class Customer {

    private String firstname;
    private String lastname;

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstname, customer.firstname) && Objects.equals(lastname, customer.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
