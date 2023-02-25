package com.link_intersystems.carrental.customer;

public class Customer {

    private CustomerId id;
    private String firstname;
    private String lastname;

    public Customer(CustomerId id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public CustomerId getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
