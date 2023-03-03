package com.link_intersystems.carmanagement.pickup;

import com.link_intersystems.carmanagement.pickupcar.FuelLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PickupCarInteractorTest {

    private PickupCarInteractor pickupCarInteractor;
    private PickupCarRepositoryMock pickupCarRepositoryMock;

    @BeforeEach
    void setUp() {
        pickupCarRepositoryMock = new PickupCarRepositoryMock();
        pickupCarInteractor = new PickupCarInteractor(pickupCarRepositoryMock);
    }

    @Test
    void pickupCar() {
        PickupCarRequestModel requestModel = new PickupCarRequestModel();
        requestModel.setCarId("");

        requestModel.setFuelLevel(FuelLevel.HALF);
        requestModel.setOdometer(15_500);


        MainDriverRequestModel mainDriver = new MainDriverRequestModel();
        mainDriver.setBookingNumber("5");
        mainDriver.setPhone("0711/12345678");
        mainDriver.setPassportNumber("T220001293");
        mainDriver.setDrivingLicenceNumber("B072RRE2I55");
        mainDriver.setFirstname("René");
        mainDriver.setLastname("Link");
        requestModel.setMainDriver(mainDriver);


        List<DriverRequestModel> additionalDrivers = new ArrayList<>();
        DriverRequestModel additionalDriver = new DriverRequestModel();
        additionalDriver.setFirstname("Stefan");
        additionalDriver.setLastname("Renz");
        additionalDriver.setDrivingLicenceNumber("N0704578035");
        additionalDrivers.add(additionalDriver);
        requestModel.setAdditionalDrivers(additionalDrivers);

        pickupCarInteractor.pickupCar(requestModel);
    }
}