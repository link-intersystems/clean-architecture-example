package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offers.CarOffersRequestModel;
import com.link_intersystems.carrental.offers.CarOffersResponseModel;
import com.link_intersystems.carrental.offers.CarOffersUseCase;

public class CarOffersUseCaseMock implements CarOffersUseCase {

    private CarOffersResponseModel responseModel;
    private CarOffersRequestModel request;

    @Override
    public CarOffersResponseModel makeOffers(CarOffersRequestModel request) {
        this.request = request;
        return responseModel;
    }

    public void setResponseModel(CarOffersResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    public boolean isInvoked() {
        return request != null;
    }
}
