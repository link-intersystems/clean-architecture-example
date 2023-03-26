package com.link_intersystems.carrental.management.pickup.list.ui;

import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarResponseModel;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.time.FixedClock;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import com.link_intersystems.swing.selection.DefaultSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListPickupCarControllerTest {

    private ListPickupCarUseCase listPickupCarUseCase;
    private MessageDialog messageDialog;
    private ListPickupCarController listPickupCarController;
    private ActionTrigger actionTrigger;

    @BeforeEach
    void setUp() {
        listPickupCarUseCase = mock(ListPickupCarUseCase.class);
        messageDialog = mock(MessageDialog.class);
        listPickupCarController = new ListPickupCarController(listPickupCarUseCase);
        listPickupCarController.setTaskExecutor(new DirectTaskExecutor());

        actionTrigger = new ActionTrigger(this);
    }

    @Test
    @FixedClock("2023-03-26 16:34:23")
    void performAction() {

        ListPickupCarModel listPickupCarModel = new ListPickupCarModel();
        listPickupCarModel.setBookingNumber("2");
        listPickupCarModel.setPickupDate("2023-03-26T16:34:23");
        Selection<ListPickupCarModel> newSelection = new DefaultSelection<>(listPickupCarModel);
        SelectionChangeEvent<ListPickupCarModel> event = new SelectionChangeEvent<>(this, Selection.empty(), newSelection);
        listPickupCarController.selectionChanged(event);

        List<ListPickupCarResponseModel> responseModels = new ArrayList<>();
        ListPickupCarResponseModel responseModel = mock(ListPickupCarResponseModel.class);
        when(responseModel.getPickupDate()).thenReturn(LocalDateTime.parse("2023-03-26T16:34:23"));
        when(responseModel.getOdometer()).thenReturn(1234);
        when(responseModel.getBookingNumber()).thenReturn(2);
        responseModels.add(responseModel);
        doReturn(responseModels).when(listPickupCarUseCase).listPickedUpCars();

        actionTrigger.performAction(listPickupCarController);

        ListModel<ListPickupCarModel> pickupCarListModel = listPickupCarController.getPickupCarListModel();
        assertEquals(1, pickupCarListModel.getSize());

        ListPickupCarModel actualListPickupCarModel = pickupCarListModel.getElementAt(0);
        assertEquals("2023-03-26T16:34:23", actualListPickupCarModel.getPickupDate());
        assertEquals("2", actualListPickupCarModel.getBookingNumber());
    }
}