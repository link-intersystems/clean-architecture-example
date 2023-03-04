package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.swing.binding.BindingValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class CarOfferPresenter {

    public CarOfferModel toCarOfferModel(CarOfferOutputModel carOfferOutputModel) {
        CarOfferModel carOfferModel = new CarOfferModel();

        carOfferModel.setId(carOfferOutputModel.getId());

        carOfferModel.setName(carOfferOutputModel.getName());

        BigDecimal totalRentalRate = carOfferOutputModel.getTotalRentalRate();
        carOfferModel.setTotalRentalRate(totalRentalRate.setScale(2, RoundingMode.HALF_UP).toString());

        BigDecimal perDayRentalRate = carOfferOutputModel.getPerDayRentalRate();
        carOfferModel.setPerDayRentalRate(perDayRentalRate.setScale(2, RoundingMode.HALF_UP).toString());

        carOfferModel.setVehicleType(carOfferOutputModel.getVehicleType());

        return carOfferModel;
    }

    public CarOfferRequestModel toRequestModel(CarSearchModel carSearchModel) {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setVehicleType(carSearchModel.getVehicleType().getValue());

        BindingValue<String> pickupDate = carSearchModel.getPickupDate();
        requestModel.setPickUpDateTime(LocalDateTime.parse(pickupDate.getValue()));

        BindingValue<String> returnDate = carSearchModel.getReturnDate();
        requestModel.setReturnDateTime(LocalDateTime.parse(returnDate.getValue()));

        return requestModel;
    }
}
