package com.link_intersystems.car.offers;

import java.time.LocalDateTime;

public class CarOffersRequestModel {

    private String vehicleType;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime returnDateTime;
    private int stationId;

    public void setVehicleType(String category) {
        this.vehicleType = category;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setPickUpDateTime(LocalDateTime pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    public LocalDateTime getPickUpDateTime() {
        return pickUpDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getStationId() {
        return stationId;
    }
}
