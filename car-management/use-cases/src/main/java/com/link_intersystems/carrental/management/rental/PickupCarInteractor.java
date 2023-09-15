package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.booking.BookingNumber;

import java.time.LocalDateTime;

class PickupCarInteractor implements PickupCarUseCase {

    private final DomainEventPublisher eventPublisher;
    private PickupCarRepository repository;

    public PickupCarInteractor(PickupCarRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void pickupCar(PickupCarRequestModel requestModel) {

        CarState carState = createCarState(requestModel);
        Driver driver = createDriver(requestModel);

        BookingNumber bookingNumber = new BookingNumber(requestModel.getBookingNumber());
        CarRental carRental = new CarRental(bookingNumber, driver, carState);

        LocalDateTime pickupDataTime = requestModel.getPickupDateTime();
        carRental.setPickupDateTime(pickupDataTime);

        repository.persist(carRental);

        CarRentalDomainEvent pickedUpEvent = CarRentalDomainEvent.pickedUpEvent(bookingNumber);
        eventPublisher.publish(pickedUpEvent);
    }

    private Driver createDriver(PickupCarRequestModel requestModel) {
        DriverRequestModel driver = requestModel.getDriver();
        String firstname = driver.getFirstname();
        String lastname = driver.getLastname();
        String drivingLicenceNumber = driver.getDrivingLicenceNumber();
        return new Driver(firstname, lastname, drivingLicenceNumber);
    }

    private CarState createCarState(PickupCarRequestModel requestModel) {
        Integer odometerValue = requestModel.getOdometer();
        Odometer odometer = Odometer.of(odometerValue);
        return new CarState(requestModel.getFuelLevel(), odometer);
    }
}
