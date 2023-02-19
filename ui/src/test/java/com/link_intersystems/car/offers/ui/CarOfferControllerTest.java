package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.offers.*;
import com.link_intersystems.time.LocalDateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarOfferControllerTest {

    private CarOffersUseCase carOffersUseCase;
    private CarOfferController carOfferController;
    private CarSearchModel carSearchModel;
    private ListModel<CarOfferModel> carOfferListModel;

    @BeforeEach
    void setUp() {
        carOffersUseCase = mock(CarOffersUseCase.class);
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

        when(carOffersUseCase.makeOffers(refEq(requestModel))).thenReturn(carOffersResponseModel);

        carSearchModel.setVehicleType("MICRO");
        carSearchModel.setPickupDate(LocalDateTimeUtils.dateTime("2023-01-15", "08:00:00"));
        carSearchModel.setReturnDate(LocalDateTimeUtils.dateTime("2023-01-17", "08:00:00"));

        carOfferController.searchCars();

        verify(carOffersUseCase, times(1)).makeOffers(any(CarOffersRequestModel.class));

        assertEquals(2, carOfferListModel.getSize());
    }
}