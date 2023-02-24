package com.link_intersystems.car.booking;

import java.util.Optional;
import java.util.ServiceLoader;

public class CarBookingConfig {

    public CarBookingUseCase getCarBookingUseCase() {
        return new CarBookingInteractor(getCarBookingRepository());
    }

    private CarBookingRepository getCarBookingRepository() {
        Class<CarBookingRepository> providerType = CarBookingRepository.class;
        ServiceLoader<CarBookingRepository> serviceLoader = ServiceLoader.load(providerType);
        Optional<CarBookingRepository> carOffersRepository = serviceLoader.findFirst();
        return carOffersRepository.orElseThrow(() -> new RuntimeException("Unable to find " + providerType.getName() + " provider."));
    }
}
