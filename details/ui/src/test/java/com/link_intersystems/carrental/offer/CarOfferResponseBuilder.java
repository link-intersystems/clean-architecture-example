package com.link_intersystems.carrental.offer;


public class CarOfferResponseBuilder {

    private CarOfferResponseModel responseModel = new CarOfferResponseModel();
    private CarOffersOutputModel carOffersOutputModel = new CarOffersOutputModel();

    public CarOfferResponseBuilder() {
        build();
    }

    public CarOfferResponseModel build() {
        responseModel.setCarOffersOutputModel(carOffersOutputModel);
        CarOfferResponseModel returnModel = responseModel;

        responseModel = new CarOfferResponseModel();
        carOffersOutputModel = new CarOffersOutputModel();
        return returnModel;
    }

    public void add(CarOfferOutputModel carOfferOutputModel) {
        carOffersOutputModel.addCarOffer(carOfferOutputModel);
    }
}
