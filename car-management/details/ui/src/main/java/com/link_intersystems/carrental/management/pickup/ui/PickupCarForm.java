package com.link_intersystems.carrental.management.pickup.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.link_intersystems.carrental.management.pickup.ui.SpringUtilities.*;

public class PickupCarForm<T> {

    private SpringLayout layout = new SpringLayout();
    private JPanel panel = new JPanel(layout);

    private JTextField bookingNumberField = new JTextField();
    private JTextField vinField = new JTextField();
    private JTextField pickupDateField = new JTextField();
    private JSlider fuelSlider = new JSlider();

    private PickupCarModel pickupCarModel = new PickupCarModel();

    PickupCarForm() {
        panel.add(new JLabel("Booking Number:"));
        panel.add(bookingNumberField);

        panel.add(new JLabel("VIN:"));
        panel.add(vinField);

        panel.add(new JLabel("Pickup Date:"));
        panel.add(pickupDateField);

        panel.add(new JLabel("Fuel Level:"));
        panel.add(fuelSlider);

        fuelSlider.setPaintTicks(true);
        fuelSlider.setSnapToTicks(true);
        fuelSlider.setMajorTickSpacing(25);

        makeGrid(panel, panel.getComponentCount() / 2, 2, 3, 3, 3, 3);
    }

    public void setModel(PickupCarModel pickupCarModel) {
        this.pickupCarModel = Objects.requireNonNull(pickupCarModel);

        updateForm();
    }

    private void updateForm() {
        bookingNumberField.setText(pickupCarModel.getBookingNumber());
        vinField.setText(pickupCarModel.getVin());
        LocalDateTime pickupDate = pickupCarModel.getPickupDate();
        pickupDateField.setText(pickupDate.toString());
        fuelSlider.setModel(pickupCarModel.getFuelLevel());
    }


    public Component getComponent() {
        return panel;
    }
}
