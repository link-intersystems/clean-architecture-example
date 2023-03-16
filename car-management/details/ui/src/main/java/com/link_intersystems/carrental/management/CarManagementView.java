package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarView;

import javax.swing.*;
import java.awt.*;

public class CarManagementView {

    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    public void setListCarBookingsView(ListCarBookingView listCarBookingsView){
        tabbedPane.addTab("Car Bookings", listCarBookingsView.getViewComponent());
    }

    public void setListPickupCarView(ListPickupCarView listPickupCarView){
        tabbedPane.addTab("Picked up cars", listPickupCarView.getViewComponent());
    }

    public Component getViewComponent() {
        return tabbedPane;
    }
}
