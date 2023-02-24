package com.link_intersystems.car.offers;

public class CarOffersResponseModel {

    private CarOffersOutputModel carOffersOutputModel = new CarOffersOutputModel();


    public void setCarOffersOutputModel(CarOffersOutputModel carOffersOutputModel) {
        this.carOffersOutputModel = carOffersOutputModel;
    }

    public CarOffersOutputModel getCarOffers() {
        return carOffersOutputModel;
    }

}
