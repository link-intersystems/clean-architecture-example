package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.swing.binding.BindingValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class CarOfferPresenter {

    public CarOfferModel toCarOfferModel(CarOfferResponseModel carOfferResponseModel) {
        CarOfferModel carOfferModel = new CarOfferModel();

        carOfferModel.setId(carOfferResponseModel.getId());
        carOfferModel.setName(carOfferResponseModel.getName());

        BigDecimal totalRentalRate = carOfferResponseModel.getTotalRentalRate();
        carOfferModel.setTotalRentalRate(totalRentalRate.setScale(2, RoundingMode.HALF_UP).toString());

        BigDecimal perDayRentalRate = carOfferResponseModel.getPerDayRentalRate();
        carOfferModel.setPerDayRentalRate(perDayRentalRate.setScale(2, RoundingMode.HALF_UP).toString());

        carOfferModel.setVehicleType(carOfferResponseModel.getVehicleType());

        carOfferModel.setPickupDateTime(carOfferResponseModel.getPickupDateTime());
        carOfferModel.setReturnDateTime(carOfferResponseModel.getReturnDateTime());

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
