package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.list.ui.CustomerModel;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarView;

import javax.swing.*;
import java.awt.*;

public class CarManagementView {

    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
    private CustomerModel customerModel;

    public void setListCarBookingsView(ListCarBookingView listCarBookingsView) {
        tabbedPane.addTab("Car Bookings", listCarBookingsView.getViewComponent());
    }

    public void setListPickupCarView(ListPickupCarView listPickupCarView) {
        tabbedPane.addTab("Picked up cars", listPickupCarView.getViewComponent());
    }

    public Component getViewComponent() {
        return tabbedPane;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }
}
