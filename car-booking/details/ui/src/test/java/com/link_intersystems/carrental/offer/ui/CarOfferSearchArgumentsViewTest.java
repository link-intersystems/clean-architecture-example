package com.link_intersystems.carrental.offer.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferSearchArgumentsViewTest {

    private CarOfferSearchArgumentsView view;
    private CarOfferSearchArgumentsViewControl viewControl;

    @BeforeEach
    void setUp() {
        view = new CarOfferSearchArgumentsView();

        CarOfferModel smartFortwo = new CarOfferModel();
        smartFortwo.setId("WMEEJ8AA3FK792135");
        smartFortwo.setName("Smart Fortwo");
        smartFortwo.setTotalRentalRate("103.55");
        smartFortwo.setVehicleType("MICRO");

        viewControl = new CarOfferSearchArgumentsViewControl(view);
    }

    @Test
    void noModelSet() {
        assertEquals("MICRO", viewControl.getVehicleType());
    }
}