package com.link_intersystems.carrental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car smartFortwo;

    @BeforeEach
    void setUp() {
        smartFortwo = new CarFixture().getSmartFortwo();
    }

    @Test
    void getId() {
        assertEquals(new CarId(new VIN("WMEEJ8AA3FK792135")), smartFortwo.getId());
    }

    @Test
    void getName() {
        assertEquals("Smart Fortwo", smartFortwo.getName());
    }

    @Test
    void getVehicleType() {
        assertEquals(VehicleType.MICRO, smartFortwo.getVehicleType());
    }

    @Test
    void getSpecs() {
        Specs specs = smartFortwo.getSpecs();

        assertNotNull(specs);
        assertEquals(new Doors(2), specs.getDoors());
        assertEquals(new Seats(2), specs.getSeats());
        assertEquals(new Consumption(FuelType.PETROL, 5.0), specs.getConsumption());
    }
}