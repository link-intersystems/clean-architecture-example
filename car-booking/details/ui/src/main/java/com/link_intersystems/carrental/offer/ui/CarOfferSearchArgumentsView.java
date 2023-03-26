package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.carrental.swing.binding.BindingValue;
import com.link_intersystems.carrental.swing.binding.text.JTextComponentBinding;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchArgumentsView {
    private JPanel searchPanel = new JPanel();
    private ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"MICRO", "SEDAN", "SUV"});
    private JComboBox<String> vehicleType = new JComboBox<>();
    private JTextField pickupDate = new JTextField();
    private JTextComponentBinding pickupDateBinding = new JTextComponentBinding(pickupDate);
    private JTextField returnDate = new JTextField();
    private JTextComponentBinding returnDateBinding = new JTextComponentBinding(returnDate);
    private JButton searchButton = new JButton();
    private CarSearchModel carSearchModel = new CarSearchModel();

    public CarOfferSearchArgumentsView() {
        vehicleType.setModel(comboBoxModel);
        vehicleType.setPreferredSize(new Dimension(200, 18));

        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        searchPanel.add(new JLabel("Vehicle type:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        searchPanel.add(vehicleType, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        searchPanel.add(new JLabel("Pick-up date:"), constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        searchPanel.add(pickupDate, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        searchPanel.add(new JLabel("Return date:"), constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        searchPanel.add(returnDate, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        searchPanel.add(searchButton, constraints);
    }

    public void setCarSearchModel(CarSearchModel carSearchModel) {
        this.carSearchModel = carSearchModel;
        bindToView(carSearchModel);
    }

    private void bindToView(CarSearchModel carSearchModel) {
        pickupDateBinding.setBindingValue(carSearchModel.getPickupDate());
        returnDateBinding.setBindingValue(carSearchModel.getReturnDate());

        BindingValue<String> vehicleType = this.carSearchModel.getVehicleType();
        comboBoxModel.setSelectedItem(vehicleType.getValue());

        this.vehicleType.addItemListener(e -> {
            Object selectedItem = comboBoxModel.getSelectedItem();
            vehicleType.setValue(String.valueOf(selectedItem));
        });
    }

    public Component getViewComponent() {
        return searchPanel;
    }

    public void setCarBookAction(Action action) {
        searchButton.setAction(action);
    }

}
