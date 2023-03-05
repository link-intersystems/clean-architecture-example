package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;

import javax.swing.*;
import java.awt.*;

public class CarManagementView {

    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    public void setListCarBookingsView(ListCarBookingView listCarBookingsView){
        tabbedPane.addTab("Car Bookings", listCarBookingsView.getViewComponent());
    }

    public Component getViewComponent() {
        return tabbedPane;
    }
}
