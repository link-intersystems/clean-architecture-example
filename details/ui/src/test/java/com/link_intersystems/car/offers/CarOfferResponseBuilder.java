package com.link_intersystems.car.offers;


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
