package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.booking.CarBookingFixture;
import com.link_intersystems.carrental.rental.RentalCarFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.link_intersystems.time.LocalDateTimeUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class CarOffersInteractorTest {

    private CarOfferInteractor carOffersInteractor;
    private CarFixture carFixture;

    @BeforeEach
    void setUp() {
        carFixture = new CarFixture();
        RentalCarFixture rentalCarFixture = new RentalCarFixture(carFixture);
        CarBookingFixture carBookingFixture = new CarBookingFixture(carFixture);
        CarOfferRepository repository = new MockCarOfferRepository(rentalCarFixture, carBookingFixture, carFixture);

        carOffersInteractor = new CarOfferInteractor(repository);
    }

    @Test
    void rentalRate() {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-18", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOfferResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

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
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOfferResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

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
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOfferResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersOutputModel carOffersOutputModel = responseModel.getCarOffers();
        assertNotNull(carOffersOutputModel);

        assertEquals(1, carOffersOutputModel.size());
    }

    @Test
    void bothCarsAvailable() {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-14", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-14", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        CarOfferResponseModel responseModel = carOffersInteractor.makeOffers(requestModel);

        CarOffersOutputModel carOffersOutputModel = responseModel.getCarOffers();
        assertNotNull(carOffersOutputModel);

        assertEquals(2, carOffersOutputModel.size());
    }
}
