package com.link_intersystems.carrental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarsByIdTest {

    private CarsById carsById;
    private CarFixture carFixture;

    @BeforeEach
    void setUp() {
        carFixture = new CarFixture();
        carsById = new CarsById(carFixture);
    }

    @Test
    void getCar() {
        Car car = carsById.getCar(new CarId(new VIN("3C3CFFBR3CTR12014")));

        assertEquals(carFixture.getFiat500(), car);
    }
}