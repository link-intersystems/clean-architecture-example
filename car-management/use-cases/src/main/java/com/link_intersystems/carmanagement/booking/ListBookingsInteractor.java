package com.link_intersystems.carmanagement.booking;

import java.time.LocalDateTime;
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
        ListBookingsResponseModel listBookingsResponseModel = new ListBookingsResponseModel();



        return listBookingsResponseModel;
    }
}
