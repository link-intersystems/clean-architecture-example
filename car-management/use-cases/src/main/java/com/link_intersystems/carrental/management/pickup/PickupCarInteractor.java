package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.booking.BookingNumber;

import java.time.LocalDateTime;

class PickupCarInteractor implements PickupCarUseCase {

    private PickupCarRepository pickupCarRepository;

    public PickupCarInteractor(PickupCarRepository pickupCarRepository) {
        this.pickupCarRepository = pickupCarRepository;
    }

    @Override
    public void pickupCar(PickupCarRequestModel requestModel) {

        CarState carState = createCarState(requestModel);
        Driver driver = createDriver(requestModel);

        BookingNumber bookingNumber = new BookingNumber(requestModel.getBookingNumber());
        CarPickup carPickup = new CarPickup(bookingNumber, driver, carState);

        LocalDateTime pickupDataTime = requestModel.getPickupDateTime();
        carPickup.setPickupDateTime(pickupDataTime);

        pickupCarRepository.persist(carPickup);
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
        if (odometerValue == null) {
            throw new IllegalStateException("odometer must be set");
        }

        Odometer odometer = new Odometer(odometerValue);
        return new CarState(requestModel.getFuelLevel(), odometer);
    }
}
