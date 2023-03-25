package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferModel;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.selection.DefaultSelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class CarBookingControllerTest {

    private CarBookingUseCaseMock carBookingUseCaseMock;
    private MessageDialogMock messageDialogMock;
    private CarBookingController carBookingController;
    private Runnable performAction;

    @BeforeEach
    void setUp() {
        carBookingUseCaseMock = new CarBookingUseCaseMock();
        messageDialogMock = new MessageDialogMock();

        Semaphore done = new Semaphore(0);
        carBookingController = new CarBookingController(carBookingUseCaseMock, messageDialogMock) {
            @Override
            protected void done(CarBookingResponseModel result) {
                super.done(result);
                done.release();
            }

            @Override
            protected void failed(ExecutionException e) {
                super.failed(e);
                done.release();
            }
        };

        ActionTrigger actionTrigger = new ActionTrigger(this);
        performAction = () -> {
            actionTrigger.performAction(carBookingController);
            done.tryAcquire();
            assertTrue(done.availablePermits() == 0, "Background action executed");
        };
    }

    @Test
    void performAction() {
        CarOfferModel carOfferModel = new CarOfferModel();
        DefaultSelection<CarOfferModel> selection = new DefaultSelection<>(carOfferModel);
        carBookingController.setCarOfferSelection(selection);
        CarBookingResponseModel responseModel = new CarBookingResponseModel();
        responseModel.setBookingNumber("123456789");
        carBookingUseCaseMock.addResponse(responseModel);

        performAction.run();

        String info = messageDialogMock.getInfo();


        assertTrue(info.contains("123456789"), () -> {
            Throwable exception = messageDialogMock.getException();
            exception.printStackTrace();
            return "info";
        });
    }
}