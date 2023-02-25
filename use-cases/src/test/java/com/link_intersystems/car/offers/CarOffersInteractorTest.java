package com.link_intersystems.car.offers;

import com.link_intersystems.car.CarFixture;
import com.link_intersystems.car.booking.CarBookingFixture;
import com.link_intersystems.car.rental.RentalCarFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.link_intersystems.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class CarOffersInteractorTest {

    private CarOffersInteractor carOffersInteractor;
    private CarFixture carFixture;

    @BeforeEach
    void setUp() {
        carFixture = new CarFixture();
        RentalCarFixture rentalCarFixture = new RentalCarFixture(carFixture);
        CarBookingFixture carBookingFixture = new CarBookingFixture(carFixture);
        CarOffersRepository repository = new MockCarOffersRepository(rentalCarFixture, carBookingFixture, carFixture);

        carOffersInteractor = new CarOffersInteractor(repository);
    }

    @Test
    void rentalRate() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-18", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersOutputModel carOffersOutputModel = responseModel.getCarOffers();
        assertNotNull(carOffersOutputModel);

        assertEquals(1, carOffersOutputModel.size(), "carOffers");
        CarOfferOutputModel carOfferOutputModel = carOffersOutputModel.get(0);

        assertEquals(carFixture.getFiat500().getId().getValue(), carOfferOutputModel.getId(), "carId");
        assertEquals(new BigDecimal("190.00"), carOfferOutputModel.getTotalRentalRate());
        assertEquals(new BigDecimal("95.00"), carOfferOutputModel.getPerDayRentalRate());
        assertEquals("MICRO", carOfferOutputModel.getVehicleType());
    }

    @Test
    void fiat500CarSpec() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersOutputModel carOffersOutputModel = responseModel.getCarOffers();
        assertNotNull(carOffersOutputModel);

        assertEquals(1, carOffersOutputModel.size(), "carOffers");
        CarOfferOutputModel carOfferOutputModel = carOffersOutputModel.get(0);

        assertEquals(carFixture.getFiat500().getId().getValue(), carOfferOutputModel.getId(), "carId");
        CarSpecModel specModel = carOfferOutputModel.getSpecModel();

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

        CarOffersOutputModel carOffersOutputModel = responseModel.getCarOffers();
        assertNotNull(carOffersOutputModel);

        assertEquals(1, carOffersOutputModel.size());
    }

    @Test
    void bothCarsAvailable() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-14", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-14", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOffersResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersOutputModel carOffersOutputModel = responseModel.getCarOffers();
        assertNotNull(carOffersOutputModel);

        assertEquals(2, carOffersOutputModel.size());
    }
}
