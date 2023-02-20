package com.link_intersystems.car.offers;

import java.util.Optional;
import java.util.ServiceLoader;

public class CarOfferConfig {

    public CarOffersUseCase getCarOfferUseCase() {
        return new CarOffersInteractor(getCarOfferRepository());
    }

    private CarOffersRepository getCarOfferRepository() {
        ServiceLoader<CarOffersRepository> serviceLoader = ServiceLoader.load(CarOffersRepository.class);
        Optional<CarOffersRepository> carOffersRepository = serviceLoader.findFirst();
        return carOffersRepository.orElseThrow(() -> new RuntimeException("Unable to find CarOfferRepository provider."));
    }
}
