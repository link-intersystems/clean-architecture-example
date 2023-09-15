package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReturnCarInteractorTest {

    private ReturnCarInteractor interactor;
    private MockReturnCarRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockReturnCarRepository();
        interactor = new ReturnCarInteractor(repository);
    }

    @Test
    @FixedClock("2023-03-26 10:32:19")
    void returnCar() {
        ReturnCarRequestModel request = new ReturnCarRequestModel();
        request.setOdometer(12345);
        request.setFuelLevel(FuelLevel.FULL);
        request.setBookingNumber(42);
        request.setReturnDateTime(ClockProvider.now());

        Driver driver = new Driver("René", "Link", "ABC");
        CarState carState = new CarState(FuelLevel.HALF, Odometer.of(1234));
        CarRental carRental = new CarRental(new BookingNumber(42), driver, carState);
        carRental.setPickupDateTime(LocalDateTime.of(2023, 3, 25, 10, 19, 23));
        repository.addCarRental(carRental);

        interactor.returnCar(request);

        CarRental latestPersistedCarRental = repository.getLatestPersistedCarRental();

        assertNotNull(latestPersistedCarRental);
        LocalDateTime expectedReturnDate = LocalDateTime.of(2023, 3, 26, 10, 32, 19);
        assertEquals(expectedReturnDate, latestPersistedCarRental.getReturnDateTime());
    }
}