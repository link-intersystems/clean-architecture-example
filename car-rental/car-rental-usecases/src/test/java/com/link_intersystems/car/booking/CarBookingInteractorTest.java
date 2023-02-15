package com.link_intersystems.car.booking;

import com.link_intersystems.time.LocalDateTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarBookingInteractorTest {

    private CarBookingInteractor carBookingInteractor;

    @BeforeEach
    void setUp() {
        MockCarBookingRepository repository = new MockCarBookingRepository();
        carBookingInteractor = new CarBookingInteractor(repository);
    }

    @Test
    void bookFiat500For3Days() throws CarNotAvailableException {
        CarBookingRequestModel requestModel = new CarBookingRequestModel();
        requestModel.setPickUpDateTime(LocalDateTimeUtils.dateTime("2018-05-13", "08:00:00"));
        requestModel.setReturnDateTime(LocalDateTimeUtils.dateTime("2018-05-16", "17:00:00"));

        CarBookingResponseModel responseModel = carBookingInteractor.bookCar(requestModel);

        assertNotNull(responseModel, "responseModel");
    }
}