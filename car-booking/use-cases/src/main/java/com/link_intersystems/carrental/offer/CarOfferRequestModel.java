package com.link_intersystems.carrental.offer;

import java.time.LocalDateTime;

public class CarOfferRequestModel {

    private String vehicleType;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;

    public void setVehicleType(String category) {
        this.vehicleType = category;
    }

    String getVehicleType() {
        return vehicleType;
    }

    public void setPickUpDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    LocalDateTime getPickUpDateTime() {
        return pickupDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }
}
