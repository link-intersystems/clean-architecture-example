package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.list.CarBookingResponseModel;
import com.link_intersystems.carrental.management.booking.list.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarBookingPresenterTest {

    private CarBookingResponseModel responseModel;
    private CarBookingPresenter carBookingPresenter;

    @BeforeEach
    void setUp() {
        carBookingPresenter = new CarBookingPresenter();

        responseModel = mock(CarBookingResponseModel.class);
        when(responseModel.getVIN()).thenReturn("WMEEJ8AA3FK792135");
        when(responseModel.getBookingNumber()).thenReturn(1);

        CustomerResponseModel customer = mock(CustomerResponseModel.class);
        when(customer.getFirstname()).thenReturn("Nick");
        when(customer.getLastname()).thenReturn("Wahlberg");
        when(responseModel.getCustomer()).thenReturn(customer);
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