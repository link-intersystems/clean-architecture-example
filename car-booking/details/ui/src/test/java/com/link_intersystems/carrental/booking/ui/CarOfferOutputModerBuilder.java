package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarSpecModel;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CarOfferOutputModerBuilder {

    private CarOfferResponseModel carOfferResponseModel = mock(CarOfferResponseModel.class);

    public CarOfferOutputModerBuilder setId(String id) {
        when(carOfferResponseModel.getId()).thenReturn(id);
        return this;
    }

    public CarOfferOutputModerBuilder setTotalRentalRate(String totalRentalRate) {
        return setTotalRentalRate(new BigDecimal(totalRentalRate));
    }

    public CarOfferOutputModerBuilder setTotalRentalRate(BigDecimal totalRentalRate) {
        when(carOfferResponseModel.getTotalRentalRate()).thenReturn(totalRentalRate);
        return this;
    }

    public CarOfferOutputModerBuilder setPerDayRentalRate(String perDayRentalRate) {
        return setPerDayRentalRate(new BigDecimal(perDayRentalRate));
    }

    public CarOfferOutputModerBuilder setPerDayRentalRate(BigDecimal perDayRentalRate) {
        when(carOfferResponseModel.getPerDayRentalRate()).thenReturn(perDayRentalRate);
        return this;
    }

    public CarOfferOutputModerBuilder withSpecModel(int seats, int doors, String energyType, double consumption) {
        CarSpecModel carSpecModel = mock(CarSpecModel.class);

        when(carSpecModel.getSeats()).thenReturn(seats);
        when(carSpecModel.getDoors()).thenReturn(doors);
        when(carSpecModel.getEnergyType()).thenReturn(energyType);
        when(carSpecModel.getConsumption()).thenReturn(consumption);

        when(carOfferResponseModel.getSpecModel()).thenReturn(carSpecModel);
        return this;
    }

    public CarOfferOutputModerBuilder setVehicleType(String vehicleType) {
        when(carOfferResponseModel.getVehicleType()).thenReturn(vehicleType);
        return this;
    }

    public CarOfferResponseModel build() {
        CarOfferResponseModel returnModel = carOfferResponseModel;
        carOfferResponseModel = mock(CarOfferResponseModel.class);
        return returnModel;
    }
}
