package com.link_intersystems.carrental.management.returnCar.ui;

import com.link_intersystems.carrental.management.booking.list.ui.CustomerModel;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.management.booking.ui.BookingNumberModel;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarResponseModelMock;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarUseCaseMock;
import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.management.returnCar.ReturnCarRequestModel;
import com.link_intersystems.carrental.management.returnCar.ReturnCarUseCaseMock;
import com.link_intersystems.carrental.swing.notification.MessageDialogMock;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.FixedClock;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class ReturnCarFormControllerTest {

    private ReturnCarUseCaseMock returnCarUseCase;
    private MessageDialogMock messageDialog;
    private ReturnCarFormController returnCarFormController;
    private ActionTrigger actionTrigger;

    private ReturnCarForm returnCarForm;
    private GetPickupCarUseCaseMock getPickupCarUseCase;

    @BeforeEach
    void setUp() {
        getPickupCarUseCase = new GetPickupCarUseCaseMock();
        returnCarUseCase = new ReturnCarUseCaseMock();
        messageDialog = new MessageDialogMock();
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

        messageDialog.whenShowDialog("Return car").thenReturn(() -> {
            ReturnCarModel returnCarFormModel = returnCarForm.getModel();
            returnCarFormModel.setOdometer("123456");
            returnCarFormModel.getFuelModel().setValue(75);
            returnCarFormModel.setReturnDate("2023-03-26T16:34:23");
            return JOptionPane.OK_OPTION;
        });

        GetPickupCarResponseModelMock responseModel = new GetPickupCarResponseModelMock();
        responseModel.setOdometer(12345);
        responseModel.setFuelLevel(FuelLevel.HALF);
        responseModel.setBookingNumber(42);
        responseModel.setPickupDate("2023-03-24", "16:34:23");
        getPickupCarUseCase.whenGetPickupCar(42).thenReturn(responseModel);

        actionTrigger.performAction(returnCarFormController);

        ReturnCarRequestModel requestModel = new ReturnCarRequestModel();
        requestModel.setFuelLevel(FuelLevel.THREE_QUARTER);
        requestModel.setOdometer(123456);
        requestModel.setBookingNumber(42);
        requestModel.setReturnDateTime(ClockProvider.now());

        returnCarUseCase.verifyReturnCar(1).returnCar(requestModel);
    }

}