package com.link_intersystems.rental;

import com.link_intersystems.car.CarId;

public class CarRental {

    private int stationId;
    private CarId carId;
    private int customerId;
    private RentalPeriod rentalPeriod;

    public CarRental(int stationId, CarId carId, int customerId, RentalPeriod rentalPeriod) {
        this.stationId = stationId;
        this.carId = carId;
        this.customerId = customerId;
        this.rentalPeriod = rentalPeriod;
    }

    public int getStationId() {
        return stationId;
    }

    public CarId getCarId() {
        return carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public RentalPeriod getRentalPeriod() {
        return rentalPeriod;
    }
}
