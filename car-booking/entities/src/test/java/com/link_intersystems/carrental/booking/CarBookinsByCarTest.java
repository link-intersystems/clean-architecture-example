package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBookinsByCarTest {

    private CarBookinsByCar carBookinsByCar;

    @BeforeEach
    void setUp() {
        CarBookingFixture carBookingFixture = new CarBookingFixture(new CarFixture());
        carBookinsByCar = new CarBookinsByCar(carBookingFixture);
    }

    @Test
    void contains() {
        assertTrue(carBookinsByCar.contains(new CarId(new VIN("WMEEJ8AA3FK792135"))));
        assertFalse(carBookinsByCar.contains(new CarId(new VIN("WBAFR1C54BCC47391"))));
    }
}