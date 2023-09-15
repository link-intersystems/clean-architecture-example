package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.CarBookingResponseModelMock;
import com.link_intersystems.carrental.management.booking.CustomerResponseModelMock;
import com.link_intersystems.carrental.management.booking.ListBookingsUseCaseMock;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ListCarBookingControllerTest {

    private ListBookingsUseCaseMock listBookingsUseCase;
    private ListCarBookingController controller;
    private ActionTrigger actionTrigger;

    @BeforeEach
    void setUp() {
        listBookingsUseCase = new ListBookingsUseCaseMock();
        controller = new ListCarBookingController(listBookingsUseCase);
        controller.setTaskExecutor(new DirectTaskExecutor());
        actionTrigger = new ActionTrigger(this);
    }

    @Test
    void performAction() {
        CarBookingResponseModelMock responseModel = new CarBookingResponseModelMock();
        responseModel.setBookingNumber(123);
        responseModel.setVIN("YV4952CYXE1702329");
        CustomerResponseModelMock customer = new CustomerResponseModelMock();
        customer.setFirstname("René");
        customer.setLastname("Link");
        responseModel.setCustomer(customer);
        listBookingsUseCase.addResponseModel(responseModel);

        actionTrigger.performAction(controller);

        ListModel<ListCarBookingModel> carBookingListModel = controller.getCarBookingListModel();
        assertEquals(1, carBookingListModel.getSize());
        ListCarBookingModel model = carBookingListModel.getElementAt(0);
        assertEquals("123", model.getBookingNumber());
        assertEquals("YV4952CYXE1702329", model.getVin());
        CustomerModel customerModel = model.getCustomerModel();
        assertEquals("René", customerModel.getFirstname());
        assertEquals("Link", customerModel.getLastname());
    }
}