package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.management.booking.list.CarBookingResponseModel;
import com.link_intersystems.carrental.management.booking.list.CustomerResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarBookingPresenterTest {

    @Test
    void toCarBookingModel() {
        CarBookingPresenter carBookingPresenter = new CarBookingPresenter();

        CarBookingResponseModel responseModel = Mockito.mock(CarBookingResponseModel.class);
        when(responseModel.getVin()).thenReturn("WMEEJ8AA3FK792135");
        when(responseModel.getBookingNumber()).thenReturn(1);
        CustomerResponseModel customer = mock(CustomerResponseModel.class);
        when(customer.getFirstname()).thenReturn("Nick");
        when(customer.getLastname()).thenReturn("Wahlberg");
        when(responseModel.getCustomer()).thenReturn(customer);

        ListCarBookingModel listCarBookingModel = carBookingPresenter.toCarBookingModel(responseModel);


        CustomerModel customerModel = listCarBookingModel.getCustomerModel();
        assertEquals("Nick", customerModel.getFirstname());
        assertEquals("Wahlberg", customerModel.getLastname());
    }

}