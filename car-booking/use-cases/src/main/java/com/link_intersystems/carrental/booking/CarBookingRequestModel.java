package com.link_intersystems.carrental.booking;

import java.time.LocalDateTime;

public class CarBookingRequestModel {
    private int customerId;
    private String carId;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;

    int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    LocalDateTime getPickUpDateTime() {
        return pickupDateTime;
    }

    public void setPickUpDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }
}
