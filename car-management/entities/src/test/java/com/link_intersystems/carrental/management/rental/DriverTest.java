package com.link_intersystems.carrental.management.rental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    @Test
    void equalsAndHashCode() {
        Driver driver1 = new Driver("René", "Link", "ABC");
        Driver driver2 = new Driver("René", "Link", "ABC");

        assertEquals(driver1, driver2);
        assertEquals(driver1.hashCode(), driver2.hashCode());

        Driver driver3 = new Driver("John", "Doe", "CBA");
        assertNotEquals(driver1, driver3);
    }
}
