package com.link_intersystems.carrental.offer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CarOfferResponseModel {

    private String id;
    private String name;
    private String vehicleType;
    private BigDecimal totalRentalRate;
    private BigDecimal perDayRentalRate;
    private CarSpecModel carSpecModel;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;

    void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public BigDecimal getTotalRentalRate() {
        return totalRentalRate;
    }

    void setTotalRentalRate(BigDecimal totalRentalRate) {
        this.totalRentalRate = totalRentalRate;
    }

    public BigDecimal getPerDayRentalRate() {
        return perDayRentalRate;
    }

    void setPerDayRentalRate(BigDecimal perDayRentalRate) {
        this.perDayRentalRate = perDayRentalRate;
    }

    public CarSpecModel getSpecModel() {
        return carSpecModel;
    }

    void setCarSpecModel(CarSpecModel carSpecModel) {
        this.carSpecModel = carSpecModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }
}
