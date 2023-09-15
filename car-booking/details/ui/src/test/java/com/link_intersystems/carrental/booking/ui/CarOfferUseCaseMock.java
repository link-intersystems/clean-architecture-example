package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.booking.CarOfferRequestModel;
import com.link_intersystems.carrental.booking.CarOfferResponseModel;
import com.link_intersystems.carrental.booking.CarOfferUseCase;

import java.util.List;

public class CarOfferUseCaseMock implements CarOfferUseCase {

    private List<CarOfferResponseModel> response;
    private CarOfferRequestModel request;

    @Override
    public List<CarOfferResponseModel> makeOffers(CarOfferRequestModel request) {
        this.request = request;
        return response;
    }

    public void setResponse(List<CarOfferResponseModel> response) {
        this.response = response;
    }

    public boolean isInvoked() {
        return request != null;
    }
}
