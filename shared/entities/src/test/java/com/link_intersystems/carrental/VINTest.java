package com.link_intersystems.carrental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VINTest {

    @Test
    void wrongVin() {
        assertThrows(IllegalArgumentException.class, () -> new VIN("WMEEJ8AA3FK795"));
    }

    @Test
    void equalsAndHashCode() {
        VIN vin1 = new VIN("WMEEJ8AA3FK792135");
        VIN vin2 = new VIN("WMEEJ8AA3FK792135");

        assertEquals(vin1, vin2);
        assertEquals(vin1.hashCode(), vin2.hashCode());

        VIN vin3 = new VIN("YV4952CYXE1702329");
        assertNotEquals(vin1, vin3);
    }
}