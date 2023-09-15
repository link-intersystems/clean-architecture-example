package com.link_intersystems.carrental.booking;

import java.time.LocalDateTime;

public class CarOfferRequestModel {

    private String vehicleType;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;

    String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String category) {
        this.vehicleType = category;
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
