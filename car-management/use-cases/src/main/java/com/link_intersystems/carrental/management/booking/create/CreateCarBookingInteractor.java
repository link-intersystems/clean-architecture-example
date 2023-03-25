package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.management.booking.CarBooking;

import static java.util.Objects.*;

class CreateCarBookingInteractor implements CreateCarBookingUseCase {

    private CreateCarBookingRepository repository;

    public CreateCarBookingInteractor(CreateCarBookingRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public void createCarBooking(CreateCarBookingRequestModel requestModel) {
        String vinStr = requestModel.getVIN();
        VIN vin = new VIN(vinStr);
        CarBooking carBooking = new CarBooking(requestModel.getBookingNumber(), vin);
        repository.persist(carBooking);
    }
}
