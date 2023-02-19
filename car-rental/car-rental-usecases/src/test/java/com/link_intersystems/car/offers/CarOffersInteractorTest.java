package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.booking.CarBookingFixture;
import com.link_intersystems.car.rental.RentalCarFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.link_intersystems.time.LocalDateTimeUtils.dateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarOffersInteractorTest {

    private CarOffersInteractor carOffersInteractor;
    private CarFixture carFixture;

    @BeforeEach
    void setUp() {
        carFixture = new CarFixture();
        RentalCarFixture rentalCarFixture = new RentalCarFixture(carFixture);
        CarBookingFixture carBookingFixture = new CarBookingFixture(carFixture);
        CarOffersRepository repository = new MockCarOffersRepository(rentalCarFixture, carBookingFixture);

        carOffersInteractor = new CarOffersInteractor(repository);
    }

    @Test
    void rentalRate() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-18", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersModel carOffersModel = responseModel.getCarOffers();
        assertNotNull(carOffersModel);

        assertEquals(1, carOffersModel.size(), "carOffers");
        CarOfferModel carOfferModel = carOffersModel.get(0);

        assertEquals(carFixture.getFiat500().getId().getValue(), carOfferModel.getId(), "carId");
        assertEquals(new BigDecimal("190.00"), carOfferModel.getTotalRentalRate());
        assertEquals(new BigDecimal("95.00"), carOfferModel.getPerDayRentalRate());
        assertEquals("MICRO", carOfferModel.getVehicleType());
    }

    @Test
    void fiat500CarSpec() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersModel carOffersModel = responseModel.getCarOffers();
        assertNotNull(carOffersModel);

        assertEquals(1, carOffersModel.size(), "carOffers");
        CarOfferModel carOfferModel = carOffersModel.get(0);

        assertEquals(carFixture.getFiat500().getId().getValue(), carOfferModel.getId(), "carId");
        CarSpecModel specModel = carOfferModel.getSpecModel();

        assertNotNull(specModel, "carSpecModel");
        assertEquals(5, specModel.getSeats());
        assertEquals(3, specModel.getDoors());
        assertEquals(4.6, specModel.getConsumption());
        assertEquals("PETROL", specModel.getEnergyType());

    }

    @Test
    void oneCarAvailableOneIsNotAvailable() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersModel carOffersModel = responseModel.getCarOffers();
        assertNotNull(carOffersModel);

        assertEquals(1, carOffersModel.size());
    }

    @Test
    void bothCarsAvailable() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-14", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-14", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersModel carOffersModel = responseModel.getCarOffers();
        assertNotNull(carOffersModel);

        assertEquals(2, carOffersModel.size());
    }
}
