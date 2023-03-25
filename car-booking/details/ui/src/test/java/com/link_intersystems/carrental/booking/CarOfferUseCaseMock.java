package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferOutputModel;
import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.offer.CarOfferUseCase;

import java.util.List;

public class CarOfferUseCaseMock implements CarOfferUseCase {

    private List<CarOfferOutputModel> response;
    private CarOfferRequestModel request;

    @Override
    public List<CarOfferOutputModel> makeOffers(CarOfferRequestModel request) {
        this.request = request;
        return response;
    }

    public void setResponse(List<CarOfferOutputModel> response) {
        this.response = response;
    }

    public boolean isInvoked() {
        return request != null;
    }
}
