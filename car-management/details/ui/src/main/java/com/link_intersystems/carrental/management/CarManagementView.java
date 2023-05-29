package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarView;
import com.link_intersystems.carrental.ui.ApplicationModel;
import com.link_intersystems.carrental.ui.View;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CarManagementView implements View {

    private ListCarBookingView listCarBookingsView;
    private ListPickupCarView listPickupCarView;

    public void setListCarBookingsView(ListCarBookingView listCarBookingsView) {
        this.listCarBookingsView = listCarBookingsView;
    }

    public void setListPickupCarView(ListPickupCarView listPickupCarView) {
        this.listPickupCarView = listPickupCarView;
    }


    @Override
    public String getTitle() {
        return "Car Management";
    }

    @Override
    public Component createComponent(ApplicationModel applicationModel) {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);


        if (listCarBookingsView != null) {
            tabbedPane.addTab("Car Bookings", listCarBookingsView.getViewComponent());
        }

        if (listPickupCarView != null) {
            tabbedPane.addTab("Picked up cars", listPickupCarView.getViewComponent());
        }

        return tabbedPane;
    }

    @Override
    public List<String> getRequiredRoles() {
        return Arrays.asList("MANAGER");
    }
}
