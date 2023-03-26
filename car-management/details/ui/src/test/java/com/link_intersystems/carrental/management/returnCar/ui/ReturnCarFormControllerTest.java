package com.link_intersystems.carrental.management.returnCar.ui;

import com.link_intersystems.carrental.management.booking.list.ui.CustomerModel;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarResponseModel;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.management.returnCar.ReturnCarRequestModel;
import com.link_intersystems.carrental.management.returnCar.ReturnCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.FixedClock;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReturnCarFormControllerTest {

    private ReturnCarUseCase returnCarUseCase;
    private MessageDialog messageDialog;
    private ReturnCarFormController returnCarFormController;
    private ActionTrigger actionTrigger;

    private ReturnCarForm returnCarForm;
    private GetPickupCarUseCase getPickupCarUseCase;

    @BeforeEach
    void setUp() {
        getPickupCarUseCase = mock(GetPickupCarUseCase.class);
        returnCarUseCase = mock(ReturnCarUseCase.class);
        messageDialog = mock(MessageDialog.class);
        returnCarFormController = new ReturnCarFormController(getPickupCarUseCase, messageDialog, returnCarUseCase) {

            @Override
            protected ReturnCarForm createReturnCarForm() {
                returnCarForm = super.createReturnCarForm();
                return returnCarForm;
            }

        };
        returnCarFormController.setTaskExecutor(new DirectTaskExecutor());

        actionTrigger = new ActionTrigger(this);
    }

    @Test
    @FixedClock("2023-03-26 16:34:23")
    void performAction() {
        ListCarBookingModel listCarBookingModel = new ListCarBookingModel();
        listCarBookingModel.setBookingNumber("42");
        listCarBookingModel.setVin("WMEEJ8AA3FK792135");
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstname("René");
        customerModel.setLastname("Link");
        listCarBookingModel.setCustomerModel(customerModel);
        Selection<BookingNumberModel> selection = new DefaultSelection<>(new BookingNumberModel(42));
        SelectionChangeEvent<BookingNumberModel> event = new SelectionChangeEvent<>(this, Selection.empty(), selection);
        returnCarFormController.selectionChanged(event);

        when(messageDialog.showDialog(anyString(), any(Component.class))).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                ReturnCarModel returnCarFormModel = returnCarForm.getModel();
                returnCarFormModel.setOdometer("123456");
                returnCarFormModel.getFuelModel().setValue(75);
                returnCarFormModel.setReturnDate("2023-03-26T16:34:23");
                return JOptionPane.OK_OPTION;
            }
        });

        GetPickupCarResponseModel responseModel = mock(GetPickupCarResponseModel.class);
        when(responseModel.getOdometer()).thenReturn(12345);
        when(responseModel.getFuelLevel()).thenReturn(FuelLevel.HALF);
        when(responseModel.getBookingNumber()).thenReturn(42);
        when(responseModel.getPickupDate()).thenReturn(LocalDateTime.parse("2023-03-24T16:34:23"));
        when(getPickupCarUseCase.getPickupCar(42)).thenReturn(responseModel);

        actionTrigger.performAction(returnCarFormController);

        ReturnCarRequestModel requestModel = new ReturnCarRequestModel();
        requestModel.setFuelLevel(FuelLevel.THREE_QUARTER);
        requestModel.setOdometer(123456);
        requestModel.setBookingNumber(42);
        requestModel.setReturnDateTime(ClockProvider.now());

        verify(returnCarUseCase).returnCar(refEq(requestModel));
    }

}