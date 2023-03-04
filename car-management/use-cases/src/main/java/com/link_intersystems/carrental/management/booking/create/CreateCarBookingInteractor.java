package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.management.booking.CarBooking;

class CreateCarBookingInteractor implements CreateCarBookingUseCase {

    private CreateCarBookingRepository repository;

    public CreateCarBookingInteractor(CreateCarBookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createCarBooking(CreateCarBookingRequestModel requestModel) {
        CarBooking carBooking = new CarBooking(requestModel.getBookingNumber(), requestModel.getVIN());
        repository.persist(carBooking);
    }
}
