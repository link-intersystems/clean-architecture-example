package com.link_intersystems.car.rental;

import com.link_intersystems.car.CarId;
import com.link_intersystems.person.customer.CustomerId;

import java.time.LocalDateTime;

/**
 * Represents an ongoing or finished rental of a {@link RentalCar}, depending on the value of the
 * {@link #getReturnDateTime()} property.
 */
public class CarRental {

    private CarId carId;
    private CustomerId customerId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;


    public CarRental(CarId carId, CustomerId customerId, LocalDateTime pickupDateTime) {
        this.carId = carId;
        this.customerId = customerId;
        this.pickupDateTime = pickupDateTime;
    }

    public CarId getCarId() {
        return carId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }
}
