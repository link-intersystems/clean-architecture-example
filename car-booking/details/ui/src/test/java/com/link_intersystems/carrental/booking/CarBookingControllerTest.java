package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.offer.CarOfferModel;
import com.link_intersystems.carrental.offer.CarSearchModel;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

        CountDownLatch done = new CountDownLatch(1);
        carBookingController = new CarBookingController(carBookingUseCaseMock,  messageDialogMock) {
            @Override
            protected void done(CarBookingResponseModel result) {
                super.done(result);
                done.countDown();
            }

            @Override
            protected void failed(ExecutionException e) {
                super.failed(e);
                done.countDown();
            }
        };

        ActionTrigger actionTrigger = new ActionTrigger(this);
        performAction = () -> {
            actionTrigger.performAction(carBookingController);
            try {
                boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                        getInputArguments().toString().contains("-agentlib:jdwp");
                if (isDebug) {
                    done.await();
                } else {
                    done.await(1, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertTrue(done.getCount() == 0, "Background action executed");
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