package com.link_intersystems.carrental.booking;


import com.link_intersystems.carrental.offers.CarOfferOutputModel;
import com.link_intersystems.carrental.offers.CarOffersOutputModel;
import com.link_intersystems.carrental.offers.CarOffersResponseModel;

public class CarOfferResponseBuilder {

    private CarOffersResponseModel responseModel = new CarOffersResponseModel();
    private CarOffersOutputModel carOffersOutputModel = new CarOffersOutputModel();

    public CarOfferResponseBuilder() {
        build();
    }

    public CarOffersResponseModel build() {
        responseModel.setCarOffersOutputModel(carOffersOutputModel);
        CarOffersResponseModel returnModel = responseModel;

        responseModel = new CarOffersResponseModel();
        carOffersOutputModel = new CarOffersOutputModel();
        return returnModel;
    }

    public void add(CarOfferOutputModel carOfferOutputModel) {
        carOffersOutputModel.addCarOffer(carOfferOutputModel);
    }
}
