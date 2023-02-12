package com.link_intersystems.car.offers;

import com.link_intersystems.car.Car;
import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.CarId;
import com.link_intersystems.rental.CarRentalFixture;
import com.link_intersystems.rental.RentalRateFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.link_intersystems.time.LocalDateTimeUtils.dateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarOffersInteractorTest {

    private CarOffersInteractor carOffersInteractor;

    @BeforeEach
    void setUp() {
        CarOffersRepository repository = new MockCarOffersRepository(new CarFixture(), new CarRentalFixture(), new RentalRateFixture());

        carOffersInteractor = new CarOffersInteractor(repository);
    }

    @Test
    void carOffer() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setStationId(1);
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-18", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.findOffers(requestModel);

        CarOffers carOffers = responseModel.getCarOffers();
        assertNotNull(carOffers);

        assertEquals(1, carOffers.size());
        CarOffer carOffer = carOffers.get(0);

        assertEquals(2, carOffer.getId());
        assertEquals(new BigDecimal("190.00"), carOffer.getTotalRentalRate());
    }

    @Test
    void oneCarAvailableOneIsNotAvailable() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setStationId(1);
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.findOffers(requestModel);

        CarOffers carOffers = responseModel.getCarOffers();
        assertNotNull(carOffers);

        assertEquals(1, carOffers.size());
    }

    @Test
    void bothCarsAvailable() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setStationId(1);
        requestModel.setPickUpDateTime(dateTime("2023-01-14", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-14", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.findOffers(requestModel);

        CarOffers carOffers = responseModel.getCarOffers();
        assertNotNull(carOffers);

        assertEquals(2, carOffers.size());
    }
}
