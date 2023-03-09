package com.link_intersystems.carrental.offer;

public class CarOfferUseCaseConfig {

    public CarOfferUseCase getCarOfferUseCase(CarOfferRepository carOfferRepository) {
        return new CarOfferInteractor(carOfferRepository);
    }

}
