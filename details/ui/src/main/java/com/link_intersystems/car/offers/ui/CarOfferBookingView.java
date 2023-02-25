package com.link_intersystems.car.offers.ui;

import javax.swing.*;
import java.awt.*;

public class CarOfferBookingView {

    private JPanel viewComponent = new JPanel();
    private JButton bookingButton = new JButton();

    public CarOfferBookingView() {
        viewComponent.setLayout(new BoxLayout(viewComponent, BoxLayout.PAGE_AXIS));
        viewComponent.add(bookingButton);
    }

    public void setBookCarAction(Action action) {
        bookingButton.setAction(action);
    }

    public Component getViewComponent() {
        return viewComponent;
    }
}
