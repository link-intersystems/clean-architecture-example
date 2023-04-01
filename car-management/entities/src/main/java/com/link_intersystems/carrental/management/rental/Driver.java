package com.link_intersystems.carrental.management.rental;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(getFirstname(), driver.getFirstname()) &&
                Objects.equals(getLastname(), driver.getLastname()) &&
                Objects.equals(getDrivingLicenceNumber(), driver.getDrivingLicenceNumber());
    }

    @Override
    public int hashCode() {
        return hash(
                getFirstname(),
                getLastname(),
                getDrivingLicenceNumber()
        );
    }
}
