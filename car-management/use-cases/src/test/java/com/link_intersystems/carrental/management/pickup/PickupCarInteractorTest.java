package com.link_intersystems.carrental.management.pickup;

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

        useCase.pickupCar(requestModel);

        assertHandover(repository.getCarHandover(), pickupDataTime);
        assertDriver(repository.getCarHandover().getDriver());
        assertCarState(repository.getCarHandover().getCarState());
    }

    private void assertHandover(CarPickup carPickup, LocalDateTime pickupDataTime) {
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