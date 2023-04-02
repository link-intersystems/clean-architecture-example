package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionChangeEvent;
import com.link_intersystems.swing.selection.SelectionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class ListCarBookingViewTest implements SelectionListener<ListCarBookingModel> {

    private DefaultListModel<ListCarBookingModel> listModel;
    private ListCarBookingViewControl viewControl;
    private SelectionChangeEvent<ListCarBookingModel> latestSelectionEvent;
    private ListCarBookingModel listCarBookingModel;

    @Override
    public void selectionChanged(SelectionChangeEvent<ListCarBookingModel> event) {
        this.latestSelectionEvent = event;
    }

    private ListCarBookingView listCarBookingView;

    @BeforeEach
    void setUp() {
        latestSelectionEvent = null;
        listCarBookingView = new ListCarBookingView();

        listModel = new DefaultListModel<>();
        listCarBookingModel = new ListCarBookingModel();
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstname("René");
        customerModel.setLastname("Link");
        listCarBookingModel.setCustomerModel(customerModel);
        listCarBookingModel.setVin("WMEEJ8AA3FK792135");
        listCarBookingModel.setBookingNumber("123");
        listModel.addElement(listCarBookingModel);

        listCarBookingView.setListCarBookingModel(listModel);
        listCarBookingView.addSelectionChangedListener(this);

        viewControl = new ListCarBookingViewControl(listCarBookingView);
    }

    @Test
    void selectCarBooking() {
        viewControl.selectRow(0);

        assertNotNull(latestSelectionEvent);
        Selection<ListCarBookingModel> selection = latestSelectionEvent.getNewSelection();
        assertTrue(!selection.isEmpty());
        assertEquals(listCarBookingModel, selection.getFirstElement());
    }

    @Test
    void cellValues() {
        assertEquals("123", viewControl.getValue(0,0));
        assertEquals("WMEEJ8AA3FK792135", viewControl.getValue(0,1));
        assertEquals("René Link", viewControl.getValue(0,2));
    }
}