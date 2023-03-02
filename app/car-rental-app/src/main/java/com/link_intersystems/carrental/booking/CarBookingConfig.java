package com.link_intersystems.carrental.booking;

public class CarBookingConfig {

    public CarBookingUseCase getCarBookingUseCase(CarBookingRepository carBookingRepository) {
        return new CarBookingInteractor(carBookingRepository);
    }

}
