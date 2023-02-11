package com.link_intersystems.car.offers;

public class CarOffersResponseModel {

    private CarOffers carOffers = new CarOffers();

    CarOffersResponseModel() {
    }

    public CarOffers getFilmListing() {
        return carOffers;
    }

    void setFilmListing(CarOffers carOffers) {
        this.carOffers = carOffers;
    }
}
