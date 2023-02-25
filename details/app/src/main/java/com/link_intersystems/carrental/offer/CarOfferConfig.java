package com.link_intersystems.carrental.offer;

import com.link_intersystems.plugins.ApplicationContext;

public class CarOfferConfig {

    public CarOffersUseCase getCarOfferUseCase(ApplicationContext applicationContext) {
        CarOffersRepository carOffersRepository = applicationContext.getService(CarOffersRepository.class);
        return new CarOffersInteractor(carOffersRepository);
    }

}
