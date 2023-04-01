package com.link_intersystems.carrental.rental;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.money.Amount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalCarTest {

    @Test
    void properties() {
        RentalCar rentalCar = new RentalCar(new CarId(new VIN("WMEEJ8AA3FK792135")), new Amount("70.55"));

        assertEquals(new CarId(new VIN("WMEEJ8AA3FK792135")), rentalCar.getCarId());
        assertEquals(new Amount("70.55"), rentalCar.getRentalRate());
    }
}