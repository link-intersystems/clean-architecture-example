package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.time.Period;

public class CarRental {

    private CarId carId;
    private int customerId;
    private Period rentalPeriod;

    public CarRental(CarId carId, int customerId, Period rentalPeriod) {
        this.carId = carId;
        this.customerId = customerId;
        this.rentalPeriod = rentalPeriod;
    }

    public CarId getCarId() {
        return carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Period getRentalPeriod() {
        return rentalPeriod;
    }
}
