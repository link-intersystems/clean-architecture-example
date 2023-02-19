package com.link_intersystems.car.offers;

import java.time.LocalDateTime;

public class CarOffersRequestModel {

    private String vehicleType;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime returnDateTime;

    public void setVehicleType(String category) {
        this.vehicleType = category;
    }

    String getVehicleType() {
        return vehicleType;
    }

    public void setPickUpDateTime(LocalDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    LocalDateTime getPickUpDateTime() {
        return pickUpDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }
}
