package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.list.CarBookingResponseModelMock;
import com.link_intersystems.carrental.management.booking.list.CustomerResponseModelMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarBookingPresenterTest {

    private CarBookingResponseModelMock responseModel;
    private CarBookingPresenter carBookingPresenter;

    @BeforeEach
    void setUp() {
        carBookingPresenter = new CarBookingPresenter();

        responseModel = new CarBookingResponseModelMock();
        responseModel.setVIN("WMEEJ8AA3FK792135");
        responseModel.setBookingNumber(1);

        CustomerResponseModelMock customer = new CustomerResponseModelMock();
        customer.setFirstname("Nick");
        customer.setLastname("Wahlberg");
        responseModel.setCustomer(customer);
    }

    @Test
    void toCarBookingModels() {
        List<ListCarBookingModel> listCarBookingModels = carBookingPresenter.toCarBookingModels(Arrays.asList(responseModel));

        assertEquals(1, listCarBookingModels.size());

        ListCarBookingModel listCarBookingModel = listCarBookingModels.get(0);

        assertEquals("1", listCarBookingModel.getBookingNumber());
        assertEquals("WMEEJ8AA3FK792135", listCarBookingModel.getVin());

        CustomerModel customerModel = listCarBookingModel.getCustomerModel();
        assertEquals("Nick", customerModel.getFirstname());
        assertEquals("Wahlberg", customerModel.getLastname());
    }

}