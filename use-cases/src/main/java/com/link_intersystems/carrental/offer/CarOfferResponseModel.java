package com.link_intersystems.carrental.offer;

public class CarOfferResponseModel {

    private CarOffersOutputModel carOffersOutputModel = new CarOffersOutputModel();


    public void setCarOffersOutputModel(CarOffersOutputModel carOffersOutputModel) {
        this.carOffersOutputModel = carOffersOutputModel;
    }

    public CarOffersOutputModel getCarOffers() {
        return carOffersOutputModel;
    }

}
