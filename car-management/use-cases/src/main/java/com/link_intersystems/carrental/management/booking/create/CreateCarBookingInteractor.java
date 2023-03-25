package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.VIN;
import com.link_intersystems.carrental.booking.BookingNumber;
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
        BookingNumber bookingNumber = new BookingNumber(requestModel.getBookingNumber());
        CarBooking carBooking = new CarBooking(bookingNumber, vin);
        repository.persist(carBooking);
    }
}
