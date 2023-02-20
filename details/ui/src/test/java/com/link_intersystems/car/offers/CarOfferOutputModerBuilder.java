package com.link_intersystems.car.offers;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CarOfferOutputModerBuilder {

    private CarOfferOutputModel carOfferOutputModel = mock(CarOfferOutputModel.class);

    public CarOfferOutputModerBuilder setId(String id) {
        when(carOfferOutputModel.getId()).thenReturn(id);
        return this;
    }

    public CarOfferOutputModerBuilder setTotalRentalRate(String totalRentalRate) {
        return setTotalRentalRate(new BigDecimal(totalRentalRate));
    }

    public CarOfferOutputModerBuilder setTotalRentalRate(BigDecimal totalRentalRate) {
        when(carOfferOutputModel.getTotalRentalRate()).thenReturn(totalRentalRate);
        return this;
    }

    public CarOfferOutputModerBuilder setPerDayRentalRate(String perDayRentalRate) {
        return setPerDayRentalRate(new BigDecimal(perDayRentalRate));
    }

    public CarOfferOutputModerBuilder setPerDayRentalRate(BigDecimal perDayRentalRate) {
        when(carOfferOutputModel.getPerDayRentalRate()).thenReturn(perDayRentalRate);
        return this;
    }

    public CarOfferOutputModerBuilder withSpecModel(int seats, int doors, String energyType, double consumption) {
        CarSpecModel carSpecModel = mock(CarSpecModel.class);
        when(carSpecModel.getSeats()).thenReturn(seats);
        when(carSpecModel.getDoors()).thenReturn(doors);
        when(carSpecModel.getEnergyType()).thenReturn(energyType);
        when(carSpecModel.getConsumption()).thenReturn(consumption);

        when(carOfferOutputModel.getSpecModel()).thenReturn(carSpecModel);
        return this;
    }

    public CarOfferOutputModerBuilder setVehicleType(String vehicleType) {
        when(carOfferOutputModel.getVehicleType()).thenReturn(vehicleType);
        return this;
    }

    public CarOfferOutputModel build() {
        CarOfferOutputModel returnModel = carOfferOutputModel;
        carOfferOutputModel = mock(CarOfferOutputModel.class);
        return returnModel;
    }
}
