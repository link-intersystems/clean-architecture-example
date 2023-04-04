package com.link_intersystems.carrental.management.rental.pickup.ui;

import com.link_intersystems.carrental.management.booking.list.ui.CustomerModel;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.management.rental.pickup.DriverRequestModel;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarRequestModel;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarUseCaseMock;
import com.link_intersystems.carrental.management.rental.FuelLevel;
import com.link_intersystems.carrental.swing.notification.MessageDialogMock;
import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.FixedClock;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class PickupCarControllerTest {

    private PickupCarUseCaseMock pickupCarUseCase;
    private MessageDialogMock messageDialog;
    private PickupCarController pickupCarController;
    private ActionTrigger actionTrigger;

    private PickupCarForm<PickupCarModel> pickupCarForm;

    @BeforeEach
    void setUp() {
        pickupCarUseCase = new PickupCarUseCaseMock();
        messageDialog = new MessageDialogMock();
        pickupCarController = new PickupCarController(pickupCarUseCase, messageDialog) {
            @Override
            protected PickupCarForm<PickupCarModel> createPickupCarForm() {
                pickupCarForm = super.createPickupCarForm();
                return pickupCarForm;
            }
        };
        actionTrigger = new ActionTrigger(this);
    }

    @Test
    void emptySelection() {
        actionTrigger.performAction(pickupCarController);
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
        Selection<ListCarBookingModel> selection = new DefaultSelection<>(listCarBookingModel);
        SelectionChangeEvent<ListCarBookingModel> event = new SelectionChangeEvent<>(this, Selection.empty(), selection);
        pickupCarController.selectionChanged(event);

        messageDialog.whenShowDialog("Pickup Car").thenReturn(() -> {
            PickupCarModel pickupCarFormModel = pickupCarForm.getModel();
            pickupCarFormModel.setOdometer("12345");
            pickupCarFormModel.setDriverLicence("ABC");
            pickupCarFormModel.getFuelLevel().setValue(50);
            pickupCarForm.setModel(pickupCarFormModel);
            return JOptionPane.OK_OPTION;
        });

        actionTrigger.performAction(pickupCarController);

        PickupCarRequestModel requestModel = new PickupCarRequestModel();
        DriverRequestModel driverModel = new DriverRequestModel();
        driverModel.setFirstname("René");
        driverModel.setLastname("Link");
        driverModel.setDrivingLicenceNumber("ABC");
        requestModel.setDriver(driverModel);
        requestModel.setFuelLevel(FuelLevel.HALF);
        requestModel.setOdometer(12345);
        requestModel.setPickupDateTime(ClockProvider.now());
        requestModel.setBookingNumber(42);

        pickupCarUseCase.verifyPickupCar(1).pickupCar(requestModel);
    }
}