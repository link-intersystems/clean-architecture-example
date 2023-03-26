package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.RentalState;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.CarState;
import com.link_intersystems.carrental.management.rental.Driver;
import com.link_intersystems.carrental.management.rental.Odometer;

import java.time.LocalDateTime;

class PickupCarInteractor implements PickupCarUseCase {

    private PickupCarRepository repository;

    public PickupCarInteractor(PickupCarRepository repository) {
        this.repository = repository;
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

        CarBooking carBooking = repository.findBooking(bookingNumber);
        carBooking.setRentalState(RentalState.PICKED_UP);

        repository.persist(carBooking);
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
