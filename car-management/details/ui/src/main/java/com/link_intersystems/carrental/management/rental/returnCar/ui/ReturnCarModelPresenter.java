package com.link_intersystems.carrental.management.rental.returnCar.ui;

import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarResponseModel;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarRequestModel;

import javax.swing.*;
import java.time.LocalDateTime;

public class ReturnCarModelPresenter {

    public ReturnCarModel toReturnCarModel(GetPickupCarResponseModel result) {
        ReturnCarModel returnCarModel = new ReturnCarModel();

        returnCarModel.setReturnDate(formatReturnDate(LocalDateTime.now()));
        returnCarModel.setBookingNumber(Integer.toString(result.getBookingNumber()));
        returnCarModel.setOdometer(Integer.toString(result.getOdometer()));
        returnCarModel.getFuelModel().setValue(result.getFuelLevel().getPercent());

        return returnCarModel;
    }

    private String formatReturnDate(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    public ReturnCarRequestModel toRequestModel(ReturnCarModel returnCarModel) {
        ReturnCarRequestModel requestModel = new ReturnCarRequestModel();

        requestModel.setBookingNumber(Integer.parseInt(returnCarModel.getBookingNumber()));

        String odometer = returnCarModel.getOdometer();
        requestModel.setOdometer(Integer.parseInt(odometer));

        BoundedRangeModel fuelModel = returnCarModel.getFuelModel();
        requestModel.setFuelLevel(FuelLevel.ofPercentage(fuelModel.getValue()));

        String returnDate = returnCarModel.getReturnDate();
        requestModel.setReturnDateTime(LocalDateTime.parse(returnDate));

        return requestModel;
    }


}
