package com.link_intersystems.car.offers.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

class CarOfferSearchArgumentsView {
    private JPanel searchPanel = new JPanel();
    private ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"MICRO", "SEDAN", "SUV"});
    private JComboBox<String> vehicleType = new JComboBox<>();
    private JTextField pickUpDate = new JTextField();
    private JTextComponentBinding pickupDateBinding = new JTextComponentBinding(pickUpDate);
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
        searchPanel.add(pickUpDate, constraints);

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

        pickUpDate.setText(carSearchModel.getPickupDate());
        returnDate.setText(carSearchModel.getReturnDate());

        pickupDateBinding.setDocumentTextConsumer(carSearchModel::setPickupDate);
        returnDateBinding.setDocumentTextConsumer(carSearchModel::setReturnDate);

        String vehicleType = this.carSearchModel.getVehicleType();
        comboBoxModel.setSelectedItem(vehicleType);

        this.vehicleType.addItemListener(e -> {
            Object selectedItem = comboBoxModel.getSelectedItem();
            carSearchModel.setVehicleType(String.valueOf(selectedItem));
        });
    }

    public Component getViewComponent() {
        return searchPanel;
    }

    public void setCarBookAction(Action action) {
        searchButton.setAction(action);
    }

    private void updateCarSearchModel() {
        Object selectedItem = vehicleType.getSelectedItem();
        carSearchModel.setVehicleType(String.valueOf(selectedItem));
        carSearchModel.setPickupDate(pickUpDate.getText().trim());
        carSearchModel.setReturnDate(returnDate.getText().trim());
    }

    class CarSearchModelBinging {

        private DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }


        };

        private CarSearchModel carSearchModel;

        public void bind(CarSearchModel carSearchModel) {
            this.carSearchModel = carSearchModel;
        }


    }


}
