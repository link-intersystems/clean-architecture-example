package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.CarState;
import com.link_intersystems.carrental.management.rental.Driver;
import com.link_intersystems.carrental.management.rental.FuelLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PickupCarInteractorTest {

    private PickupCarInteractor useCase;
    private PickupCarRepositoryMock repository;

    @BeforeEach
    void setUp() {
        repository = new PickupCarRepositoryMock();
        useCase = new PickupCarInteractor(repository);
    }

    @Test
    void pickupCar() {
        PickupCarRequestModel requestModel = new PickupCarRequestModel();

        requestModel.setBookingNumber(42);
        requestModel.setFuelLevel(FuelLevel.HALF);
        requestModel.setOdometer(15_500);

        DriverRequestModel mainDriver = new DriverRequestModel();
        mainDriver.setDrivingLicenceNumber("B072RRE2I55");
        mainDriver.setFirstname("John");
        mainDriver.setLastname("Doe");
        requestModel.setDriver(mainDriver);
        LocalDateTime pickupDataTime = LocalDateTime.of(2023, 2, 15, 8, 0, 0);
        requestModel.setPickupDateTime(pickupDataTime);

        CarBooking carBooking = new CarBooking(1, new VIN("WMEEJ8AA3FK792135"));
        repository.setCarBooking(carBooking);

        useCase.pickupCar(requestModel);

        assertHandover(repository.getCarRental(), pickupDataTime);
        assertDriver(repository.getCarRental().getDriver());
        assertCarState(repository.getCarRental().getPickupCarState());
    }

    private void assertHandover(CarRental carPickup, LocalDateTime pickupDataTime) {
        assertEquals(42, carPickup.getBookingNumber().getValue());
        assertEquals(pickupDataTime, carPickup.getPickupDateTime());
    }

    private void assertCarState(CarState carState) {
        assertEquals(FuelLevel.HALF, carState.getFuelLevel());
        assertEquals(Integer.valueOf(15_500), carState.getOdometer().getValue());
    }

    private void assertDriver(Driver driver) {
        assertEquals("John", driver.getFirstname());
        assertEquals("Doe", driver.getLastname());
        assertEquals("B072RRE2I55", driver.getDrivingLicenceNumber());
    }
}