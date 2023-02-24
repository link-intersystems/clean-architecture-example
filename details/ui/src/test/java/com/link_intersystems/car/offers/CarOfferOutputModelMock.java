package com.link_intersystems.car.offers;

import java.math.BigDecimal;

public class CarOfferOutputModelMock extends CarOfferOutputModel{

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

    @Override
    public void setTotalRentalRate(BigDecimal totalRentalRate) {
        super.setTotalRentalRate(totalRentalRate);
    }

    @Override
    void setPerDayRentalRate(BigDecimal perDayRentalRate) {
        super.setPerDayRentalRate(perDayRentalRate);
    }

    @Override
    void setCarSpecModel(CarSpecModel carSpecModel) {
        super.setCarSpecModel(carSpecModel);
    }
}
