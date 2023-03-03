package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferOutputModel;
import com.link_intersystems.carrental.offer.CarOfferOutputModelMock;
import com.link_intersystems.carrental.offer.CarSpecModelMock;

import java.math.BigDecimal;

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
        CarSpecModelMock carSpecModel = new CarSpecModelMock();

        carSpecModel.setSeats(seats);
        carSpecModel.setDoors(doors);
        carSpecModel.setEnergyType(energyType);
        carSpecModel.setConsumption(consumption);

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