package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.*;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.carrental.time.LocalDateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferControllerTest {

    private CarOfferUseCaseMock carOffersUseCase;
    private CarOfferController carOfferController;
    private CarSearchModel carSearchModel;
    private ListModel<CarOfferModel> carOfferListModel;
    private Semaphore controllerDone;

    @BeforeEach
    void setUp() {
        controllerDone = new Semaphore(0);

        carOffersUseCase = new CarOfferUseCaseMock();
        carSearchModel = new CarSearchModel();
        carOfferController = new CarOfferController(carOffersUseCase, carSearchModel) {
            @Override
            protected void done(CarOfferResponseModel responseModel) {
                super.done(responseModel);
                controllerDone.release();
            }
        };
        carOfferListModel = carOfferController.getCarOfferListModel();
    }

    @Test
    void searchCars() throws InterruptedException {
        CarOfferRequestModel requestModel = new CarOfferRequestModel();
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
        CarOfferResponseModel carOfferResponseModel = responseBuilder.build();

        carOffersUseCase.setResponseModel(carOfferResponseModel);

        carSearchModel.getVehicleType().setValue("MICRO");
        carSearchModel.getPickupDate().setValue("2023-01-15T08:00:00");
        carSearchModel.getReturnDate().setValue("2023-01-17T08:00:00");

        new ActionTrigger(this).performAction(carOfferController);
        controllerDone.acquire();

        assertTrue(carOffersUseCase.isInvoked());

        assertEquals(2, carOfferListModel.getSize());
    }
}