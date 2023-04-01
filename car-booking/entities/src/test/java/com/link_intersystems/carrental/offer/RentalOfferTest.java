package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.money.Amount;
import com.link_intersystems.carrental.rental.RentalCar;
import com.link_intersystems.carrental.rental.RentalCarFixture;
import com.link_intersystems.carrental.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.link_intersystems.carrental.time.PeriodBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class RentalOfferTest {

    private RentalOffer rentalOffer;
    private RentalCar smartForTwo;

    @BeforeEach
    void setUp() {
        RentalCarFixture rentalCars = new RentalCarFixture(new CarFixture());
        Period period = from("2023-01-15", "08:00:00").to("2023-01-17", "17:00:00");
        smartForTwo = rentalCars.getSmartForTwo();
        rentalOffer = new RentalOffer(smartForTwo, period);
    }

    @Test
    void getTotalRentalAmount() {
        assertEquals(new Amount("269.97"), rentalOffer.getTotalRentalAmount());
    }

    @Test
    void getRentalCar() {
        assertEquals(smartForTwo, rentalOffer.getRentalCar());
    }
}