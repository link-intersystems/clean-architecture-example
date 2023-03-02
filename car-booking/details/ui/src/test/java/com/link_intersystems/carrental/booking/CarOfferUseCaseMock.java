package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarOfferUseCase;

public class CarOfferUseCaseMock implements CarOfferUseCase {

    private CarOfferResponseModel responseModel;
    private CarOfferRequestModel request;

    @Override
    public CarOfferResponseModel makeOffers(CarOfferRequestModel request) {
        this.request = request;
        return responseModel;
    }

    public void setResponseModel(CarOfferResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    public boolean isInvoked() {
        return request != null;
    }
}
