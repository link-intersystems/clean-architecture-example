package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offers.CarOfferOutputModel;
import com.link_intersystems.carrental.offers.CarSpecModel;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CarOfferOutputModerBuilder {

    private CarOfferOutputModelMock carOfferOutputModel = new CarOfferOutputModelMock();

    public CarOfferOutputModerBuilder setId(String id) {
        carOfferOutputModel.setId(id);
        return this;
    }

    public CarOfferOutputModerBuilder setTotalRentalRate(String totalRentalRate) {
        return setTotalRentalRate(new BigDecimal(totalRentalRate));
    }

    public CarOfferOutputModerBuilder setTotalRentalRate(BigDecimal totalRentalRate) {
        carOfferOutputModel.setTotalRentalRate(totalRentalRate);
        return this;
    }

    public CarOfferOutputModerBuilder setPerDayRentalRate(String perDayRentalRate) {
        return setPerDayRentalRate(new BigDecimal(perDayRentalRate));
    }

    public CarOfferOutputModerBuilder setPerDayRentalRate(BigDecimal perDayRentalRate) {
        carOfferOutputModel.setPerDayRentalRate(perDayRentalRate);
        return this;
    }

    public CarOfferOutputModerBuilder withSpecModel(int seats, int doors, String energyType, double consumption) {
        CarSpecModel carSpecModel = mock(CarSpecModel.class);
        when(carSpecModel.getSeats()).thenReturn(seats);
        when(carSpecModel.getDoors()).thenReturn(doors);
        when(carSpecModel.getEnergyType()).thenReturn(energyType);
        when(carSpecModel.getConsumption()).thenReturn(consumption);

        carOfferOutputModel.setCarSpecModel(carSpecModel);
        return this;
    }

    public CarOfferOutputModerBuilder setVehicleType(String vehicleType) {
        carOfferOutputModel.setVehicleType(vehicleType);
        return this;
    }

    public CarOfferOutputModel build() {
        CarOfferOutputModel returnModel = carOfferOutputModel;
        carOfferOutputModel = new CarOfferOutputModelMock();
        return returnModel;
    }
}
