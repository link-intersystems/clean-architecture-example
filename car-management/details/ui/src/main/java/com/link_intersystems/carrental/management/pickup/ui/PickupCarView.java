package com.link_intersystems.carrental.management.pickup.ui;

import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;

import java.awt.*;

public class PickupCarView {

    private BeanEditForm<ListCarBookingModel> beanEditForm = new BeanEditForm<>(ListCarBookingModel.class);


    public Component getViewComponent() {
        return beanEditForm.getComponent();
    }
}
