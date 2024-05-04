package com.link_intersystems.carrental.offer.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarOfferBookingViewTest {

    private CarOfferBookingView carOfferBookingView;
    private ViewControl viewControl;

    @BeforeEach
    void setUp() {
        carOfferBookingView = new CarOfferBookingView();
        viewControl = new ViewControl(carOfferBookingView.getViewComponent());
    }

    @Test
    void bookingButtonAction() {
        MockAction mockAction = new MockAction("Book car");
        carOfferBookingView.setBookCarAction(mockAction);

        viewControl.clickButton("Book car");

        mockAction.assertPerformed();
    }
}