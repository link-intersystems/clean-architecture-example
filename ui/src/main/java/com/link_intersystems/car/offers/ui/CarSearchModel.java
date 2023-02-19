package com.link_intersystems.car.offers.ui;

import java.time.LocalDateTime;

public class CarSearchModel {
    private String vehicleType;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }
}
