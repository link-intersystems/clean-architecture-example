package com.link_intersystems.car.offers;

import java.util.Optional;
import java.util.ServiceLoader;

public class CarOfferUseCaseConfig {

    private CarOffersInteractor carOffersInteractor;

    public CarOffersUseCase getCarOfferUseCase() {
        if (carOffersInteractor == null) {
            ServiceLoader<CarOffersRepository> serviceLoader = ServiceLoader.load(CarOffersRepository.class);
            Optional<CarOffersRepository> carOffersRepository = serviceLoader.findFirst();
            carOffersInteractor = carOffersRepository.map(CarOffersInteractor::new).orElseThrow(() -> new RuntimeException("Unable to find CarOfferRepository provider."));
        }
        return carOffersInteractor;
    }
}
