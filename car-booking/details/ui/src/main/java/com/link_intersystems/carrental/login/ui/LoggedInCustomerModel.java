package com.link_intersystems.carrental.login.ui;

public class LoggedInCustomerModel {

    private int id = -1;
    private String firstname = "";
    private String lastname = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void clear() {
        this.id = -1;
        this.firstname = "";
        this.lastname = "";
    }
}
