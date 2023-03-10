package com.link_intersystems.carrental.management.pickup;

import static java.util.Objects.*;

public class Driver {

    private String firstname;
    private String lastname;
    private String drivingLicenceNumber;

    public Driver(String firstname, String lastname, String drivingLicenceNumber) {
        this.firstname = requireNonNull(firstname);
        this.lastname = requireNonNull(lastname);
        this.drivingLicenceNumber = requireNonNull(drivingLicenceNumber);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDrivingLicenceNumber() {
        return drivingLicenceNumber;
    }
}
