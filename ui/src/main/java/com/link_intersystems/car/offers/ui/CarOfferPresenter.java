package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.CarOfferOutputModel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CarOfferPresenter {

    public CarOfferModel toPresentationModel(CarOfferOutputModel carOfferOutputModel) {
        CarOfferModel carOfferModel = new CarOfferModel();

        carOfferModel.setId(carOfferOutputModel.getId());

        BigDecimal totalRentalRate = carOfferOutputModel.getTotalRentalRate();
        carOfferModel.setTotalRentalRate(totalRentalRate.setScale(2, RoundingMode.HALF_UP).toString());

        BigDecimal perDayRentalRate = carOfferOutputModel.getPerDayRentalRate();
        carOfferModel.setPerDayRentalRate(perDayRentalRate.setScale(2, RoundingMode.HALF_UP).toString());

        carOfferModel.setVehicleType(carOfferOutputModel.getVehicleType());

        return carOfferModel;
    }
}
