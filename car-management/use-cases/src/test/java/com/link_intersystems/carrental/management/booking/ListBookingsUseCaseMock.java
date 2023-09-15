package com.link_intersystems.carrental.management.booking;

import java.util.ArrayList;
import java.util.List;

public class ListBookingsUseCaseMock implements ListBookingsUseCase {

    private List<CarBookingResponseModel> responseModels = new ArrayList<>();

    @Override
    public List<CarBookingResponseModel> listBookings() {
        return responseModels;
    }

    public void addResponseModel(CarBookingResponseModel responseModel) {
        this.responseModels.add(responseModel);
    }
}
