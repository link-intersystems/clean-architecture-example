package com.link_intersystems.carrental.offer.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CarOfferSearchResultViewTest {

    private CarOfferSearchResultView carOfferSearchResultView;
    private CarOfferSearchResultViewControl viewControl;
    private CarOfferModel smartFortwo;
    private CarOfferModel fiat500;

    @BeforeEach
    void setUp() {
        carOfferSearchResultView = new CarOfferSearchResultView();
        smartFortwo = new CarOfferModel();
        smartFortwo.setId("WMEEJ8AA3FK792135");
        smartFortwo.setName("Smart Fortwo");
        smartFortwo.setTotalRentalRate("103.55");
        smartFortwo.setVehicleType("MICRO");

        fiat500 = new CarOfferModel();
        fiat500.setId("3C3CFFBR3CTR12014");
        fiat500.setName("Fiat 500");
        fiat500.setTotalRentalRate("125.23");
        fiat500.setVehicleType("MICRO");

        DefaultListModel<CarOfferModel> listModel = new DefaultListModel<>();
        listModel.addElement(smartFortwo);
        listModel.addElement(fiat500);

        carOfferSearchResultView.setCarOfferListModel(listModel);

        viewControl = new CarOfferSearchResultViewControl(carOfferSearchResultView);
    }

    @Test
    void tableValues() {
        assertEquals("WMEEJ8AA3FK792135", viewControl.getValueAt(0, 0));
        assertEquals("Smart Fortwo", viewControl.getValueAt(0, 1));
    }


    @Test
    void selection() {
        assertTrue(carOfferSearchResultView.getSelectionProvider().getSelection().isEmpty());

        viewControl.select(1);

        assertFalse(carOfferSearchResultView.getSelectionProvider().getSelection().isEmpty());
        assertEquals(fiat500, carOfferSearchResultView.getSelectionProvider().getSelection().getFirstElement());
    }

    @Test
    void viewComponent() {
        assertNotNull(carOfferSearchResultView.getViewComponent());
    }
}