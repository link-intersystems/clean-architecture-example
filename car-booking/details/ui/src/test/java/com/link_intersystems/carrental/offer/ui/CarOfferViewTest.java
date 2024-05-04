package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.carrental.MockAction;
import com.link_intersystems.carrental.ViewControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.TableModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarOfferViewTest {

    private CarOfferView carOfferView;
    private ViewControl viewControl;


    @BeforeEach
    void setUp() {
        carOfferView = new CarOfferView();
        viewControl = new ViewControl(carOfferView.getViewComponent());
    }


    @Test
    void bookingButtonAction() {
        MockAction mockAction = new MockAction("Book car");
        carOfferView.setBookCarAction(mockAction);

        viewControl.clickButton("Book car");

        mockAction.assertPerformed();
    }

    @Test
    void carSearchAction() {
        MockAction mockAction = new MockAction("Search Car");
        carOfferView.setCarSearchAction(mockAction);

        viewControl.clickButton("Search Car");

        mockAction.assertPerformed();
    }

    @Test
    void searchModel() {
        CarSearchModel carSearchModel = new CarSearchModel();
        carSearchModel.getVehicleType().setValue("SUV");

        carOfferView.setCarSearchModel(carSearchModel);

        JComboBox comboBox = viewControl.findComponent(JComboBox.class);

        Object selectedItem = comboBox.getSelectedItem();
        assertEquals("SUV", selectedItem);
    }

    @Test
    void carOfferListModel() {
        DefaultListModel<CarOfferModel> carOfferModelDefaultListModel = new DefaultListModel<>();
        CarOfferModel carOfferModel = new CarOfferModel();
        carOfferModel.setName("Smart");
        carOfferModelDefaultListModel.add(0, carOfferModel);

        carOfferView.setCarOfferListModel(carOfferModelDefaultListModel);

        JTable carOfferTable = viewControl.findComponent(JTable.class);

        TableModel model = carOfferTable.getModel();

        assertEquals("Smart", model.getValueAt(0, 1));
    }
}