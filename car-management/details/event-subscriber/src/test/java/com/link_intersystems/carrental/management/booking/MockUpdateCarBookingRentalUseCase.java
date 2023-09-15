package com.link_intersystems.carrental.management.booking;

public class MockUpdateCarBookingRentalUseCase implements UpdateCarBookingRentalUseCase {
    private UpdateCarBookingRentalRequestModel updateCarBookingRentalRequestModel;

    @Override
    public void updateCarRental(UpdateCarBookingRentalRequestModel updateCarBookingRentalRequestModel) {
        this.updateCarBookingRentalRequestModel = updateCarBookingRentalRequestModel;
    }

    public UpdateCarBookingRentalRequestModel getLatestRequestModel() {
        return updateCarBookingRentalRequestModel;
    }
}
