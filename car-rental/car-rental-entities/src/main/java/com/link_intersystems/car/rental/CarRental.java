package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.person.customer.CustomerId;
import com.link_intersystems.time.Period;

public class CarRental {

    private CarId carId;
    private CustomerId customerId;
    private Period rentalPeriod;

    public CarRental(CarId carId, CustomerId customerId, Period rentalPeriod) {
        this.carId = carId;
        this.customerId = customerId;
        this.rentalPeriod = rentalPeriod;
    }

    public CarId getCarId() {
        return carId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Period getRentalPeriod() {
        return rentalPeriod;
    }
}
