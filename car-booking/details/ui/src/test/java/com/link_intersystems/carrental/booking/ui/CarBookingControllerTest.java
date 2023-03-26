package com.link_intersystems.carrental.booking.ui;

import com.link_intersystems.carrental.booking.CarBookingException;
import com.link_intersystems.carrental.booking.CarBookingRequestModel;
import com.link_intersystems.carrental.booking.CarBookingResponseModel;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.offer.ui.CarOfferModel;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CarBookingControllerTest {

    private CarBookingUseCase carBookingUseCase;
    private MessageDialogMock messageDialogMock;
    private CarBookingController carBookingController;
    private ActionTrigger actionTrigger;


    @BeforeEach
    void setUp() {
        carBookingUseCase = mock(CarBookingUseCase.class);
        messageDialogMock = new MessageDialogMock();

        carBookingController = new CarBookingController(carBookingUseCase, messageDialogMock);
        carBookingController.setTaskExecutor(new DirectTaskExecutor());

        actionTrigger = new ActionTrigger(this);
    }

    @Test
    void performAction() throws CarBookingException {
        CarOfferModel carOfferModel = new CarOfferModel();
        DefaultSelection<CarOfferModel> selection = new DefaultSelection<>(carOfferModel);
        SelectionChangeEvent<CarOfferModel> selectionChangeEvent = new SelectionChangeEvent<>(this, Selection.empty(), selection);
        carBookingController.getSelectionListener().selectionChanged(selectionChangeEvent);
        CarBookingResponseModel responseModel = mock(CarBookingResponseModel.class);
        when(responseModel.getBookingNumber()).thenReturn("123456789");
        when(carBookingUseCase.bookCar(any(CarBookingRequestModel.class))).thenReturn(responseModel);

        actionTrigger.performAction(carBookingController);

        String info = messageDialogMock.getInfo();

        assertTrue(info.contains("123456789"), () -> {
            Throwable exception = messageDialogMock.getException();
            exception.printStackTrace();
            return "info";
        });
    }

    @Test
    void exception() throws CarBookingException {
        CarOfferModel carOfferModel = new CarOfferModel();
        DefaultSelection<CarOfferModel> selection = new DefaultSelection<>(carOfferModel);
        SelectionChangeEvent<CarOfferModel> selectionChangeEvent = new SelectionChangeEvent<>(this, Selection.empty(), selection);
        carBookingController.getSelectionListener().selectionChanged(selectionChangeEvent);
        CarBookingResponseModel responseModel = mock(CarBookingResponseModel.class);
        when(responseModel.getBookingNumber()).thenReturn("123456789");
        when(carBookingUseCase.bookCar(any(CarBookingRequestModel.class))).thenReturn(responseModel);

        actionTrigger.performAction(carBookingController);

        String info = messageDialogMock.getInfo();

        assertTrue(info.contains("123456789"), () -> {
            Throwable exception = messageDialogMock.getException();
            exception.printStackTrace();
            return "info";
        });
    }
}