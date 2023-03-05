package com.link_intersystems.carrental.offer;

import java.time.LocalDateTime;

public class CarOfferModel {

    private String id;
    private String totalRentalRate;
    private String perDayRentalRate;
    private String vehicleType;
    private String name;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalRentalRate() {
        return totalRentalRate;
    }

    public void setTotalRentalRate(String totalRentalRate) {
        this.totalRentalRate = totalRentalRate;
    }

    public String getPerDayRentalRate() {
        return perDayRentalRate;
    }

    public void setPerDayRentalRate(String perDayRentalRate) {
        this.perDayRentalRate = perDayRentalRate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }
}
