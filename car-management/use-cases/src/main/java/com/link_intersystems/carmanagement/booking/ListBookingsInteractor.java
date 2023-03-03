package com.link_intersystems.carmanagement.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListBookingsInteractor implements ListBookingsUseCase {

    private ListBookingsRepository listBookingsRepository;

    public ListBookingsInteractor(ListBookingsRepository listBookingsRepository) {
        this.listBookingsRepository = Objects.requireNonNull(listBookingsRepository);
    }

    @Override
    public ListBookingsResponseModel listBookings(ListBookingsRequestModel requestModel) {
        LocalDateTime from = requestModel.getFrom();
        LocalDateTime to = requestModel.getTo();

        List<CarBooking> carBookings = listBookingsRepository.findBookings(from, to);

        return toResponseModel(carBookings);
    }

    private ListBookingsResponseModel toResponseModel(List<CarBooking> carBookings) {
        ListBookingsResponseModel responseModel = new ListBookingsResponseModel();

        ArrayList<CarBookingResponseModel> carBookingsResponseModels = new ArrayList<>();
        for (CarBooking carBooking : carBookings) {
            CarBookingResponseModel carBookingResponseModel = toResponseModel(carBooking);
            carBookingsResponseModels.add(carBookingResponseModel);
        }
        responseModel.setCarBookings(carBookingsResponseModels);

        return responseModel;
    }

    private CarBookingResponseModel toResponseModel(CarBooking carBooking) {
        return new CarBookingResponseModel();
    }
}
