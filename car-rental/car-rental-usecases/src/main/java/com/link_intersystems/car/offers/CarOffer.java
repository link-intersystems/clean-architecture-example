package com.link_intersystems.car.offers;

import java.math.BigDecimal;

public class CarOffer {

    private int id;
    private String name;
    private String verhicleType;
    private BigDecimal totalRentalRate;
    private BigDecimal perDayRentalRate;

    void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    void setName(String name) {
        this.name = name;
    }

    void setVerhicleType(String verhicleType) {
        this.verhicleType = verhicleType;
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

    public void setPerDayRentalRate(BigDecimal perDayRentalRate) {
        this.perDayRentalRate = perDayRentalRate;
    }
}
