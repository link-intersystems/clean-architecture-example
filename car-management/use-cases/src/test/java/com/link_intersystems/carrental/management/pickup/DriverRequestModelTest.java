package com.link_intersystems.carrental.management.pickup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverRequestModelTest {

    @Test
    void equalsAndHashCode() {
        DriverRequestModel driverRequestModel1 = new DriverRequestModel();
        driverRequestModel1.setFirstname("René");
        driverRequestModel1.setLastname("Link");
        driverRequestModel1.setDrivingLicenceNumber("ABC");

        DriverRequestModel driverRequestModel2 = new DriverRequestModel();
        driverRequestModel2.setFirstname("René");
        driverRequestModel2.setLastname("Link");
        driverRequestModel2.setDrivingLicenceNumber("ABC");

        assertEquals(driverRequestModel1, driverRequestModel2);
        assertEquals(driverRequestModel1.hashCode(), driverRequestModel2.hashCode());

        DriverRequestModel driverRequestModel3 = new DriverRequestModel();
        driverRequestModel3.setFirstname("John");
        driverRequestModel3.setLastname("Doe");
        driverRequestModel3.setDrivingLicenceNumber("CBA");

        assertNotEquals(driverRequestModel1, driverRequestModel3);
    }
}