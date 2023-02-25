package com.link_intersystems.carrental.offers;

import java.math.BigDecimal;

public class CarOfferOutputModel {

    private String id;
    private String name;
    private String vehicleType;
    private BigDecimal totalRentalRate;
    private BigDecimal perDayRentalRate;
    private CarSpecModel carSpecModel;

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
}
