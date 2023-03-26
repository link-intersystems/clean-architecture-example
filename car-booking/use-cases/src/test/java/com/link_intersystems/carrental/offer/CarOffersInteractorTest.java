package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.CarFixture;
import com.link_intersystems.carrental.booking.CarBookingFixture;
import com.link_intersystems.carrental.rental.RentalCarFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.link_intersystems.carrental.time.LocalDateTimeUtils.*;
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

        List<CarOfferResponseModel> response = carOffersInteractor.makeOffers(requestModel);

        assertNotNull(response);

        assertEquals(1, response.size(), "carOffers");
        CarOfferResponseModel carOfferResponseModel = response.get(0);

        assertEquals(carFixture.getFiat500().getId().getValue(), carOfferResponseModel.getId(), "carId");
        assertEquals(new BigDecimal("190.00"), carOfferResponseModel.getTotalRentalRate());
        assertEquals(new BigDecimal("95.00"), carOfferResponseModel.getPerDayRentalRate());
        assertEquals("MICRO", carOfferResponseModel.getVehicleType());
    }

    @Test
    void fiat500CarSpec() {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-17", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-17", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        List<CarOfferResponseModel> response = carOffersInteractor.makeOffers(requestModel);

        assertNotNull(response);

        assertEquals(1, response.size(), "carOffers");
        CarOfferResponseModel carOfferResponseModel = response.get(0);

        assertEquals(carFixture.getFiat500().getId().getValue(), carOfferResponseModel.getId(), "carId");
        CarSpecModel specModel = carOfferResponseModel.getSpecModel();

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

        List<CarOfferResponseModel> response = carOffersInteractor.makeOffers(requestModel);

        assertNotNull(response);

        assertEquals(1, response.size());
    }

    @Test
    void bothCarsAvailable() {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setPickUpDateTime(dateTime("2023-01-14", "08:30:00"));
        requestModel.setReturnDateTime(dateTime("2023-01-14", "17:00:00"));
        requestModel.setVehicleType("MICRO");

        List<CarOfferResponseModel> response = carOffersInteractor.makeOffers(requestModel);

        assertNotNull(response);

        assertEquals(2, response.size());
    }
}
