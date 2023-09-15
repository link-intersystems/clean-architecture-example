package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.booking.BookingNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PickupCarInteractorTest {

    private PickupCarInteractor useCase;
    private PickupCarRepositoryMock repository;

    private CarRentalDomainEvent carRentalDomainEvent;

    @BeforeEach
    void setUp() {
        repository = new PickupCarRepositoryMock();
        DomainEventBus domainEventBus = new DomainEventBus();
        domainEventBus.addSubscribers(new DomainEventSubscriber() {
            @Override
            public boolean subscribedTo(Class<?> domainEventType) {
                return CarRentalDomainEvent.class.equals(domainEventType);
            }

            @Override
            public void handleEvent(Object domainEvent) {
                carRentalDomainEvent = (CarRentalDomainEvent) domainEvent;
            }
        });
        useCase = new PickupCarInteractor(repository, domainEventBus);
    }

    @Test
    void pickupCar() {
        PickupCarRequestModel requestModel = new PickupCarRequestModel();

        requestModel.setBookingNumber(1);
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

        assertHandover(repository.getCarRental(), pickupDataTime);
        assertDriver(repository.getCarRental().getDriver());
        assertCarState(repository.getCarRental().getPickupCarState());

        assertNotNull(carRentalDomainEvent);
        assertEquals(CarRentalDomainEvent.PICKED_UP, carRentalDomainEvent.getEventType());
        assertEquals(new BookingNumber(1), carRentalDomainEvent.getBookingNumber());
    }

    private void assertHandover(CarRental carPickup, LocalDateTime pickupDataTime) {
        assertEquals(1, carPickup.getBookingNumber().getValue());
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