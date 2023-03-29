package com.link_intersystems.carrental.management.pickup;

import java.util.Objects;

public class DriverRequestModel {
    private String firstname;
    private String lastname;
    private String drivingLicenceNumber;

    String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    String getDrivingLicenceNumber() {
        return drivingLicenceNumber;
    }

    public void setDrivingLicenceNumber(String drivingLicenceNumber) {
        this.drivingLicenceNumber = drivingLicenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverRequestModel that = (DriverRequestModel) o;
        return Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(drivingLicenceNumber, that.drivingLicenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, drivingLicenceNumber);
    }
}
