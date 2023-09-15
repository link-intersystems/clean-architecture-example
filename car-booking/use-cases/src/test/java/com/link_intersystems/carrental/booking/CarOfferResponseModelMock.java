package com.link_intersystems.carrental.booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CarOfferResponseModelMock extends CarOfferResponseModel {

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setVehicleType(String vehicleType) {
        super.setVehicleType(vehicleType);
    }

    public void setTotalRentalRate(String totalRentalRate) {
        setTotalRentalRate(new BigDecimal(totalRentalRate));
    }

    @Override
    public void setTotalRentalRate(BigDecimal totalRentalRate) {
        super.setTotalRentalRate(totalRentalRate);
    }

    public void setPerDayRentalRate(String perDayRentalRate) {
        setPerDayRentalRate(new BigDecimal(perDayRentalRate));
    }

    @Override
    public void setPerDayRentalRate(BigDecimal perDayRentalRate) {
        super.setPerDayRentalRate(perDayRentalRate);
    }

    @Override
    public void setCarSpecModel(CarSpecModel carSpecModel) {
        super.setCarSpecModel(carSpecModel);
    }

    @Override
    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        super.setPickupDateTime(pickupDateTime);
    }

    @Override
    public void setReturnDateTime(LocalDateTime returnDateTime) {
        super.setReturnDateTime(returnDateTime);
    }

}