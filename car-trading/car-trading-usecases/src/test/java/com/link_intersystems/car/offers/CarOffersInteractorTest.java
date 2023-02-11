package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarOffersInteractorTest {

    private CarOffersInteractor carOffersInteractor;

    @BeforeEach
    void setUp() {
        CarOffersRepository repository = mock(CarOffersRepository.class);

        CarFixture carFixture = new CarFixture();
        FindCarOffersAnswer findCarOffersAnswer = new FindCarOffersAnswer(carFixture);
        when(repository.findCars(any(CarCriteria.class))).thenAnswer(findCarOffersAnswer);

        CarOffersInteractor.Deps interactorDeps = mock(CarOffersInteractor.Deps.class);
        when(interactorDeps.getRepository()).thenReturn(repository);

        LocalDateTime fixtedDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        Clock fixedClock = Clock.fixed(fixtedDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.systemDefault());
        when(interactorDeps.getClock()).thenReturn(fixedClock);

        carOffersInteractor = new CarOffersInteractor(interactorDeps);
    }

    @Test
    void findMicroOffers() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.findOffers(requestModel);

        CarOffers carOffers = responseModel.getCarOffers();
        assertNotNull(carOffers);

        assertEquals(2, carOffers.size());
    }

    @Test
    void findOffersDateRange() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setStationId(1);
        requestModel.setPickUpDateTime(LocalDateTime.of(2023, 1,5, 8,30,0));
        requestModel.setReturnDateTime(LocalDateTime.of(2023, 1,8, 17,00,0));
        requestModel.setVehicleType("SEDAN");

        CarOffersResponseModel responseModel = carOffersInteractor.findOffers(requestModel);

        CarOffers carOffers = responseModel.getCarOffers();
        assertNotNull(carOffers);

        assertEquals(2, carOffers.size());
    }

    @Test
    void findSUVOffers() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setVehicleType("SUV");

        CarOffersResponseModel responseModel = carOffersInteractor.findOffers(requestModel);

        CarOffers carOffers = responseModel.getCarOffers();
        assertNotNull(carOffers);

        assertEquals(2, carOffers.size());
    }

}
