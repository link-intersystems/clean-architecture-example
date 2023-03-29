package com.link_intersystems.carrental.booking;

import static java.util.Objects.*;

public class CarBookingUseCaseMock implements CarBookingUseCase {

    private CarBookingResponseModel responseModel = new CarBookingResponseModelMock();
    private CarBookingException carBookingException;

    @Override
    public CarBookingResponseModel bookCar(CarBookingRequestModel request) throws CarBookingException {
        if (carBookingException != null) {
            throw carBookingException;
        }
        return responseModel;
    }

    public void setCarBookingException(CarBookingException carBookingException) {
        this.carBookingException = carBookingException;
    }

    public void setResponseModel(CarBookingResponseModel responseModel) {
        this.responseModel = requireNonNull(responseModel);
    }
}