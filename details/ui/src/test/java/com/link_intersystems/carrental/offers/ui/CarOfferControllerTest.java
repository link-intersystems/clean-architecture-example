package com.link_intersystems.carrental.offers.ui;

import com.link_intersystems.carrental.offers.CarOfferOutputModerBuilder;
import com.link_intersystems.carrental.offers.CarOfferResponseBuilder;
import com.link_intersystems.carrental.offers.CarOffersRequestModel;
import com.link_intersystems.carrental.offers.CarOffersResponseModel;
import com.link_intersystems.time.LocalDateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferControllerTest {

    private CarOffersUseCaseMock carOffersUseCase;
    private CarOfferController carOfferController;
    private CarSearchModel carSearchModel;
    private ListModel<CarOfferModel> carOfferListModel;

    @BeforeEach
    void setUp() {
        carOffersUseCase = new CarOffersUseCaseMock();
        carOfferController = new CarOfferController(carOffersUseCase);
        carSearchModel = carOfferController.getCarSearchModel();
        carOfferListModel = carOfferController.getCarOfferListModel();
    }

    @Test
    void searchCars() {
        CarOffersRequestModel requestModel = new CarOffersRequestModel();
        requestModel.setVehicleType("MICRO");
        requestModel.setPickUpDateTime(LocalDateTimeUtils.dateTime("2023-01-15", "08:00:00"));
        requestModel.setReturnDateTime(LocalDateTimeUtils.dateTime("2023-01-17", "08:00:00"));

        CarOfferResponseBuilder responseBuilder = new CarOfferResponseBuilder();
        CarOfferOutputModerBuilder carOfferOutputModerBuilder = new CarOfferOutputModerBuilder();
        responseBuilder.add( //
                carOfferOutputModerBuilder //
                        .setId("1") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("40.00")) //
                        .setTotalRentalRate(new BigDecimal("120.00")) //
                        .build());

        responseBuilder.add( //
                carOfferOutputModerBuilder //
                        .setId("2") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("30.00")) //
                        .setTotalRentalRate(new BigDecimal("90.00")) //
                        .build());
        CarOffersResponseModel carOffersResponseModel = responseBuilder.build();

        carOffersUseCase.setResponseModel(carOffersResponseModel);

        carSearchModel.getVehicleType().setValue("MICRO");
        carSearchModel.getPickupDate().setValue("2023-01-15T08:00:00");
        carSearchModel.getReturnDate().setValue("2023-01-17T08:00:00");

        carOfferController.searchCars();

        assertTrue(carOffersUseCase.isInvoked());

        assertEquals(2, carOfferListModel.getSize());
    }
}