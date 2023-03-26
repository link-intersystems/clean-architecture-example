package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferModel;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import com.link_intersystems.swing.selection.DefaultSelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBookingControllerTest {

    private CarBookingUseCaseMock carBookingUseCaseMock;
    private MessageDialogMock messageDialogMock;
    private CarBookingController carBookingController;
    private ActionTrigger actionTrigger;


    @BeforeEach
    void setUp() {
        carBookingUseCaseMock = new CarBookingUseCaseMock();
        messageDialogMock = new MessageDialogMock();

        carBookingController = new CarBookingController(carBookingUseCaseMock, messageDialogMock);
        carBookingController.setTaskExecutor(new DirectTaskExecutor());

        actionTrigger = new ActionTrigger(this);
    }

    @Test
    void performAction() {
        CarOfferModel carOfferModel = new CarOfferModel();
        DefaultSelection<CarOfferModel> selection = new DefaultSelection<>(carOfferModel);
        carBookingController.setCarOfferSelection(selection);
        CarBookingResponseModel responseModel = new CarBookingResponseModel();
        responseModel.setBookingNumber("123456789");
        carBookingUseCaseMock.addResponse(responseModel);

        actionTrigger.performAction(carBookingController);

        String info = messageDialogMock.getInfo();

        assertTrue(info.contains("123456789"), () -> {
            Throwable exception = messageDialogMock.getException();
            exception.printStackTrace();
            return "info";
        });
    }
}