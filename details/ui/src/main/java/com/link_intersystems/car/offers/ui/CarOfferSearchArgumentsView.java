package com.link_intersystems.car.offers.ui;

import com.link_intersystems.swing.action.FuncAsyncWorkLifecycle;
import com.link_intersystems.swing.action.SimpleAsyncAction;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchArgumentsView {
    private JPanel searchPanel = new JPanel();
    private ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"MICRO", "SEDAN", "SUV"});
    private JComboBox<String> vehicleType = new JComboBox<>();
    private JTextField pickUpDate = new JTextField();
    private JTextField returnDate = new JTextField();
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
        modelToView();
    }

    private void modelToView() {
        String vehicleType = carSearchModel.getVehicleType();
        comboBoxModel.setSelectedItem(vehicleType);

        pickUpDate.setText(carSearchModel.getPickupDate().toString());
        returnDate.setText(carSearchModel.getReturnDate().toString());
    }

    public Component getViewComponent() {
        return searchPanel;
    }

    public void setSearchRunnable(Runnable runnable) {
        SimpleAsyncAction<Void> searchAction = new SimpleAsyncAction<>(runnable::run);
        searchAction.setAsyncWorkLifecycle(new FuncAsyncWorkLifecycle.Builder<>().setPrepareForExecution(this::updateCarSearchModel).build());
        searchAction.putValue(Action.NAME, "Search");
        searchButton.setAction(searchAction);
    }

    private void updateCarSearchModel() {
        Object selectedItem = vehicleType.getSelectedItem();
        carSearchModel.setVehicleType(String.valueOf(selectedItem));
        carSearchModel.setPickupDate(pickUpDate.getText().trim());
        carSearchModel.setReturnDate(returnDate.getText().trim());
    }
}
