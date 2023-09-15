package com.link_intersystems.carrental.management.booking;

public class MockCreateCarBookingUseCase implements CreateCarBookingUseCase {
    private CreateCarBookingRequestModel requestModel;

    @Override
    public void createCarBooking(CreateCarBookingRequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public CreateCarBookingRequestModel getLatestRequestModel() {
        return requestModel;
    }
}
