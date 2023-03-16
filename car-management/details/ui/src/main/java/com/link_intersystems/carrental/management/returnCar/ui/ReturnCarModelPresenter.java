package com.link_intersystems.carrental.management.returnCar.ui;

import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.pickup.FuelLevel;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarModel;
import com.link_intersystems.carrental.management.returnCar.ReturnCarRequestModel;

import javax.swing.*;
import java.time.LocalDateTime;

public class ReturnCarModelPresenter {

    public ReturnCarModel toReturnCarModel(ListPickupCarModel listPickupCarModel) {
        ReturnCarModel returnCarModel = new ReturnCarModel();

        returnCarModel.setBookingNumber(listPickupCarModel.getBookingNumber());

        return returnCarModel;
    }

    public ReturnCarRequestModel toRequestModel(ReturnCarModel returnCarModel) {
        ReturnCarRequestModel requestModel = new ReturnCarRequestModel();

        String odometer = returnCarModel.getOdometer();
        requestModel.setOdometer(Integer.parseInt(odometer));

        BoundedRangeModel fuelModel = returnCarModel.getFuelModel();
        requestModel.setFuelLevel(FuelLevel.ofPercentage(fuelModel.getValue()));

        String returnDate = returnCarModel.getReturnDate();
        requestModel.setReturnDateTime(LocalDateTime.parse(returnDate));

        return requestModel;
    }

    public String formatReturnDate(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    public String formatBookingNumber(BookingNumberModel bookingNumberModel) {
        return formatBookingNumber(bookingNumberModel.getValue());
    }

    public String formatBookingNumber(int bookingNumber) {
        return Integer.toString(bookingNumber);
    }
}
