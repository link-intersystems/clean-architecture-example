package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.offer.CarOfferResponseModel;
import com.link_intersystems.carrental.offer.CarOfferRequestModel;
import com.link_intersystems.carrental.offer.ui.CarOfferController;
import com.link_intersystems.carrental.offer.ui.CarOfferModel;
import com.link_intersystems.carrental.offer.ui.CarSearchModel;
import com.link_intersystems.carrental.time.LocalDateTimeUtils;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferControllerTest {

    private CarOfferUseCaseMock carOffersUseCase;
    private CarOfferController carOfferController;
    private CarSearchModel carSearchModel;
    private ListModel<CarOfferModel> carOfferListModel;
    private ActionTrigger actionTrigger;

    @BeforeEach
    void setUp() {
        carOffersUseCase = new CarOfferUseCaseMock();
        carOfferController = new CarOfferController(carOffersUseCase);
        carOfferController.setTaskExecutor(new DirectTaskExecutor());
        carSearchModel = carOfferController.getCarSearchModel();
        carOfferListModel = carOfferController.getCarOfferListModel();

        actionTrigger = new ActionTrigger(this);
    }

    @Test
    void searchCars() throws InterruptedException {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
        requestModel.setVehicleType("MICRO");
        requestModel.setPickUpDateTime(LocalDateTimeUtils.dateTime("2023-01-15", "08:00:00"));
        requestModel.setReturnDateTime(LocalDateTimeUtils.dateTime("2023-01-17", "08:00:00"));

        List<CarOfferResponseModel> response = new ArrayList<>();
        CarOfferOutputModerBuilder carOfferOutputModerBuilder = new CarOfferOutputModerBuilder();
        response.add( //
                carOfferOutputModerBuilder //
                        .setId("1") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("40.00")) //
                        .setTotalRentalRate(new BigDecimal("120.00")) //
                        .build());

        response.add( //
                carOfferOutputModerBuilder //
                        .setId("2") //
                        .setVehicleType("MICRO") //
                        .setPerDayRentalRate(new BigDecimal("30.00")) //
                        .setTotalRentalRate(new BigDecimal("90.00")) //
                        .build());

        carOffersUseCase.setResponse(response);

        carSearchModel.getVehicleType().setValue("MICRO");
        carSearchModel.getPickupDate().setValue("2023-01-15T08:00:00");
        carSearchModel.getReturnDate().setValue("2023-01-17T08:00:00");


        actionTrigger.performAction(carOfferController);

        assertTrue(carOffersUseCase.isInvoked());

        assertEquals(2, carOfferListModel.getSize());
    }
}