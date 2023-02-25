package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.rental.RentalCar;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBUnitCarOfferRepositoryTest {

    @Test
    void findRentalCars() {
        DBUnitCarOfferRepository dbUnitCarOfferRepository = new DBUnitCarOfferRepository();
        List<RentalCar> rentalCars = dbUnitCarOfferRepository.findRentalCars(new CarCriteria());

        assertEquals(5, rentalCars.size());
    }

    @Test
    void findCarBookins() {
    }
}