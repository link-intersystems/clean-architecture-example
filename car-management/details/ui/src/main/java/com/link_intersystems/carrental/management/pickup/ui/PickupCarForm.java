package com.link_intersystems.carrental.management.pickup.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.link_intersystems.carrental.management.pickup.ui.SpringUtilities.*;
import static com.link_intersystems.events.swing.DocumentEventMethod.*;

public class PickupCarForm<T> {

    private SpringLayout layout = new SpringLayout();
    private JPanel panel = new JPanel(layout);

    private JTextField bookingNumberField = new JTextField();
    private JTextField vinField = new JTextField();
    private JTextField pickupDateField = new JTextField();
    private JTextField odometerField = new JTextField();
    private JSlider fuelSlider = new JSlider();
    private JTextField driverFirstname = new JTextField();
    private JTextField driverLastname = new JTextField();
    private JTextField driverLicence = new JTextField();

    private PickupCarModel pickupCarModel = new PickupCarModel();

    PickupCarForm() {
        panel.add(new JLabel("Booking Number:"));
        bookingNumberField.setEditable(false);
        panel.add(bookingNumberField);

        panel.add(new JLabel("VIN:"));
        vinField.setEditable(false);
        panel.add(vinField);

        panel.add(new JLabel("Pickup Date:"));
        panel.add(pickupDateField);

        panel.add(new JLabel("Fuel Level:"));
        fuelSlider.setPaintTicks(true);
        fuelSlider.setSnapToTicks(true);
        fuelSlider.setMajorTickSpacing(25);
        panel.add(fuelSlider);

        panel.add(new JLabel("Odometer:"));
        odometerField.getDocument().addDocumentListener(INSERT_UPDATE.listener(this::updateModel));
        panel.add(odometerField);


        panel.add(new JLabel("Driver Firstname:"));
        driverFirstname.getDocument().addDocumentListener(INSERT_UPDATE.listener(this::updateModel));
        panel.add(driverFirstname);

        panel.add(new JLabel("Driver Lastname:"));
        driverLastname.getDocument().addDocumentListener(INSERT_UPDATE.listener(this::updateModel));
        panel.add(driverLastname);

        panel.add(new JLabel("Driver License:"));
        driverLicence.getDocument().addDocumentListener(INSERT_UPDATE.listener(this::updateModel));
        panel.add(driverLicence);

        makeGrid(panel, panel.getComponentCount() / 2, 2, 3, 3, 3, 3);
    }

    public void setModel(PickupCarModel pickupCarModel) {
        this.pickupCarModel = Objects.requireNonNull(pickupCarModel);

        updateForm();
    }

    private void updateModel() {
        pickupCarModel.setOdometer(odometerField.getText());
        pickupCarModel.setDriverFirstname(driverFirstname.getText());
        pickupCarModel.setDriverLastname(driverLastname.getText());
        pickupCarModel.setDriverLicence(driverLicence.getText());
    }

    private void updateForm() {
        bookingNumberField.setText(pickupCarModel.getBookingNumber());
        vinField.setText(pickupCarModel.getVin());
        pickupDateField.setText(pickupCarModel.getPickupDate());
        fuelSlider.setModel(pickupCarModel.getFuelLevel());
        odometerField.setText(pickupCarModel.getOdometer());
    }


    public Component getComponent() {
        return panel;
    }

}
