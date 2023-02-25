package com.link_intersystems.carrental.booking;

import com.link_intersystems.plugins.ApplicationContext;

public class CarBookingConfig {

    public CarBookingUseCase getCarBookingUseCase(ApplicationContext applicationContext) {
        CarBookingRepository carBookingRepository = applicationContext.getService(CarBookingRepository.class);
        return new CarBookingInteractor(carBookingRepository);
    }

}
