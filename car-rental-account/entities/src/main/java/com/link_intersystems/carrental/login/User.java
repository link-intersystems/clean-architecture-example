package com.link_intersystems.carrental.login;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public class User {
    private UserId id;
    private String firstname;
    private String lastname;
    private List<String> roles;

    public User(UserId id, String firstname, String lastname, List<String> roles) {
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("A user must have at least one role");
        }

        this.id = requireNonNull(id);
        this.firstname = requireNonNull(firstname);
        this.lastname = requireNonNull(lastname);
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

    public UserId getId() {
        return id;
    }
}
