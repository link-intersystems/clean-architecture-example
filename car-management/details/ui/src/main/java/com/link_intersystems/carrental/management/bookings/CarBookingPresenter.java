package com.link_intersystems.carrental.management.bookings;

import com.link_intersystems.carmanagement.booking.CarBookingResponseModel;

import java.util.List;
import java.util.stream.Collectors;

public class CarBookingPresenter {

    public List<CarBookingModel> toCarBookingModels(List<CarBookingResponseModel> carBooking) {
        return carBooking.stream().map(this::toCarBookingModel).collect(Collectors.toList());
    }

    public CarBookingModel toCarBookingModel(CarBookingResponseModel carBooking) {
        CarBookingModel carBookingModel = new CarBookingModel();
        carBookingModel.setVin(carBooking.getVIN());
        carBookingModel.setBookingNumber(String.valueOf(carBooking.getBookingNumber()));
        return carBookingModel;
    }
}
