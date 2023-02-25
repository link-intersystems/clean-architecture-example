package com.link_intersystems.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VINTest {

    @Test
    void mercedesBenzEClass2012() {
        assertEquals("WDDHF5GB7CA482637", new VIN("WDDHF5GB7CA482637").getValue());
    }

    @Test
    void wrongVin() {
        assertThrows(IllegalArgumentException.class, () -> new VIN("WDDHF5GB7CA48263"));
    }

}