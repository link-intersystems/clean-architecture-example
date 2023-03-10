package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.list.CarBookingResponseModel;

import java.util.List;
import java.util.stream.Collectors;

public class CarBookingPresenter {

    public List<ListCarBookingModel> toCarBookingModels(List<CarBookingResponseModel> carBooking) {
        return carBooking.stream().map(this::toCarBookingModel).collect(Collectors.toList());
    }

    public ListCarBookingModel toCarBookingModel(CarBookingResponseModel carBooking) {
        ListCarBookingModel listCarBookingModel = new ListCarBookingModel();
        listCarBookingModel.setVin(carBooking.getVIN());
        listCarBookingModel.setBookingNumber(Integer.toString(carBooking.getBookingNumber()));
        return listCarBookingModel;
    }
}
