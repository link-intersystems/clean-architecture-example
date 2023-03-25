package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GetPickupCarInteractorTest {

    private MockGetPickupCarRepository repository;
    private GetPickupCarInteractor getPickupCarInteractor;

    @BeforeEach
    void setUp() {
        repository = new MockGetPickupCarRepository();
        getPickupCarInteractor = new GetPickupCarInteractor(repository);
    }

    public GetPickupCarInteractorTest() {
        super();
    }

    @Test
    @FixedClock("2023-03-25 10:19:23")
    void getPickupCar() {
        Driver driver = new Driver("René", "Link", "ABC");
        CarState pickupCarState = new CarState(FuelLevel.HALF, new Odometer(1234));
        CarRental carRental = new CarRental(new BookingNumber(42), driver, pickupCarState);
        repository.putCarRental(42, carRental);

        GetPickupCarResponseModel pickupCar = getPickupCarInteractor.getPickupCar(42);

        assertNotNull(pickupCar);

        assertEquals(42, pickupCar.getBookingNumber());
        assertEquals(1234, pickupCar.getOdometer());
        assertEquals(FuelLevel.HALF, pickupCar.getFuelLevel());

        LocalDateTime expectedPickupDate = LocalDateTime.of(2023, 3, 25, 10, 19, 23);
        assertEquals(expectedPickupDate, pickupCar.getPickupDate());
    }
}