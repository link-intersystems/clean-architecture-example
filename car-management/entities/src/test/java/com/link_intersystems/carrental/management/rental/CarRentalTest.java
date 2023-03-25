package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.time.Period;
import com.link_intersystems.carrental.time.PeriodBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CarRentalTest {

    private CarRental carRental;

    @BeforeEach
    void setUp() {
        Driver driver = new Driver("René", "Link", "ABC");
        CarState pickupCarState = new CarState(FuelLevel.HALF, Odometer.of(1234));
        carRental = new CarRental(new BookingNumber(2), driver, pickupCarState);
        carRental.setPickupDateTime(LocalDateTime.of(2023, 3, 25, 15, 4, 12));
    }

    @Test
    void returnCarEarlierThenPickup() {
        CarState returnCarState = new CarState(FuelLevel.FULL, Odometer.of(12345));
        assertThrows(IllegalArgumentException.class, () -> carRental.returnCar(returnCarState, LocalDateTime.of(2023, 3, 25, 15, 4, 11)));
    }

    @Test
    void returnCarWithLessOdometerThenPickupValue() {
        CarState returnCarState = new CarState(FuelLevel.FULL, Odometer.of(1233));
        assertThrows(IllegalArgumentException.class, () -> carRental.returnCar(returnCarState, LocalDateTime.of(2023, 3, 25, 15, 4, 15)));
    }

    @Test
    void getRentalPeriodIfNotReturned() {
        Period rentalPeriod = carRental.getRentalPeriod();

        assertNull(rentalPeriod);
    }

    @Test
    void returnCar() {
        CarState returnCarState = new CarState(FuelLevel.FULL, Odometer.of(1235));

        carRental.returnCar(returnCarState, LocalDateTime.of(2023, 3, 25, 15, 4, 15));

        Period rentalPeriod = carRental.getRentalPeriod();

        Period expectedPeriod = PeriodBuilder.from("2023-03-25", "15:04:12").to("2023-03-25", "15:04:15");
        assertEquals(expectedPeriod, rentalPeriod);
        assertEquals(returnCarState, carRental.getReturnCarState());
    }
}