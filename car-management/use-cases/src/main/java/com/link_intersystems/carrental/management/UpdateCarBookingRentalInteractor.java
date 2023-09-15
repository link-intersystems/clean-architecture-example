package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.UpdateCarBookingRentalRequestModel;
import com.link_intersystems.carrental.management.booking.UpdateCarBookingRentalUseCase;

public class UpdateCarBookingRentalInteractor implements UpdateCarBookingRentalUseCase {

    private UpdateCarBookingRentalRepository repository;

    public UpdateCarBookingRentalInteractor(UpdateCarBookingRentalRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateCarRental(UpdateCarBookingRentalRequestModel updateCarBookingRentalRequestModel) {
        CarBooking carBooking = repository.findCarBooking(updateCarBookingRentalRequestModel.bookingNumber());
        carBooking.setRentalState(updateCarBookingRentalRequestModel.rentalState());
        repository.persist(carBooking);
    }
}
