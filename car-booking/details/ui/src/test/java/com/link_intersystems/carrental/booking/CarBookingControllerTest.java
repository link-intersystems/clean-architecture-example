package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferModel;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
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
        SelectionChangeEvent<CarOfferModel> selectionChangeEvent = new SelectionChangeEvent<>(this, Selection.empty(), selection);
        carBookingController.getSelectionListener().selectionChanged(selectionChangeEvent);
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