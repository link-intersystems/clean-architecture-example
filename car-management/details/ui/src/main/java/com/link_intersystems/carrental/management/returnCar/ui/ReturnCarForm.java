package com.link_intersystems.carrental.management.returnCar.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.link_intersystems.carrental.swing.SpringUtilities.*;
import static com.link_intersystems.events.swing.DocumentEventMethod.*;

public class ReturnCarForm {

    private SpringLayout layout = new SpringLayout();
    private JPanel panel = new JPanel(layout);

    private JTextField bookingNumberField = new JTextField();
    private JTextField returnDateField = new JTextField();
    private JTextField odometerField = new JTextField();
    private JSlider fuelSlider = new JSlider();

    private ReturnCarModel returnCarModel = new ReturnCarModel();

    ReturnCarForm() {
        panel.add(new JLabel("Booking Number:"));
        bookingNumberField.setEditable(false);
        panel.add(bookingNumberField);


        panel.add(new JLabel("Return Date:"));
        panel.add(returnDateField);

        panel.add(new JLabel("Fuel Level:"));
        fuelSlider.setPaintTicks(true);
        fuelSlider.setSnapToTicks(true);
        fuelSlider.setMajorTickSpacing(25);
        panel.add(fuelSlider);

        panel.add(new JLabel("Odometer:"));
        odometerField.getDocument().addDocumentListener(INSERT_UPDATE.listener(this::updateModel));
        panel.add(odometerField);

        makeGrid(panel, panel.getComponentCount() / 2, 2, 3, 3, 3, 3);
    }

    public void setModel(ReturnCarModel returnCarModel) {
        this.returnCarModel = Objects.requireNonNull(returnCarModel);

        updateForm();
    }

    public ReturnCarModel getModel() {
        return returnCarModel;
    }

    private void updateModel() {
        returnCarModel.setBookingNumber(bookingNumberField.getText());
        returnCarModel.setReturnDate(returnDateField.getText());
        returnCarModel.setOdometer(odometerField.getText());
    }

    private void updateForm() {
        bookingNumberField.setText(returnCarModel.getBookingNumber());
        returnDateField.setText(returnCarModel.getReturnDate());
        fuelSlider.setModel(returnCarModel.getFuelModel());
        odometerField.setText(returnCarModel.getOdometer());
    }

    public Component getComponent() {
        return panel;
    }
}
