package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.offer.CarOfferUseCase;

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
