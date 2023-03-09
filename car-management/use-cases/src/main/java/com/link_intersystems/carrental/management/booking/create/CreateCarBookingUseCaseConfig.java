package com.link_intersystems.carrental.management.booking.create;

public class CreateCarBookingUseCaseConfig {

    public CreateCarBookingUseCase getCreateCarBookingUseCase(CreateCarBookingRepository createCarBookingRepository) {
        return new CreateCarBookingInteractor(createCarBookingRepository);
    }

}
