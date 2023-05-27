package com.link_intersystems.carrental.login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String firstname;
    private String lastname;
    private List<String> roles;

    public User(String firstname, String lastname, List<String> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = new ArrayList<>(roles);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<String> getRoles() {
        return new ArrayList<>(roles);
    }
}
