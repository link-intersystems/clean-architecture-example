package com.link_intersystems.carrental.offer;

import com.link_intersystems.plugins.ApplicationContext;

public class CarOfferConfig {

    public CarOfferUseCase getCarOfferUseCase(ApplicationContext applicationContext) {
        CarOfferRepository carOfferRepository = applicationContext.getService(CarOfferRepository.class);
        return new CarOfferInteractor(carOfferRepository);
    }

}
