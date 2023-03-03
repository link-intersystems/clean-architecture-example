package com.link_intersystems.carmanagement.pickup;

import com.link_intersystems.carmanagement.pickupcar.FuelLevel;

import java.time.LocalDateTime;
import java.util.List;

public class PickupCarRequestModel {
    private String carId;
    private LocalDateTime pickupDataTime;
    private FuelLevel fuelLevel;
    private Integer odometer;

    private MainDriverRequestModel mainDriver;
    private List<DriverRequestModel> additionalDrivers;

    public void setAdditionalDrivers(List<DriverRequestModel> additionalDrivers) {
        this.additionalDrivers = additionalDrivers;
    }

    public void setMainDriver(MainDriverRequestModel mainDriver) {
        this.mainDriver = mainDriver;
    }

    public void setFuelLevel(FuelLevel fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public void setPickupDataTime(LocalDateTime pickupDataTime) {
        this.pickupDataTime = pickupDataTime;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
