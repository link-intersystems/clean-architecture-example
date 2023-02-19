package com.link_intersystems.car.offers.ui;

import com.link_intersystems.swing.table.BeanListTableCellSupport;
import com.link_intersystems.swing.table.ListTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class CarOfferView {

    private JPanel viewPanel = new JPanel();

    private JPanel searchPanel = new JPanel();
    private JComboBox<String> vehicleType = new JComboBox<>();
    private JTextField pickUpDate = new JTextField();
    private JTextField returnDate = new JTextField();
    private JButton searchButton = new JButton();

    private JTable carOfferTable = new JTable();
    private JScrollPane carOfferTableScrollPane = new JScrollPane(carOfferTable);
    private CarSearchModel carSearchModel = new CarSearchModel();

    public CarOfferView() {
        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[]{"MICRO", "SEDAN", "SUV"});
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

        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(searchPanel, BorderLayout.NORTH);
        viewPanel.add(carOfferTableScrollPane, BorderLayout.CENTER);
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {

        ListTableModel<CarOfferModel> carOfferModelListTableModel = new ListTableModel<>();

        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.setListTableCellRenderer(BeanListTableCellSupport.of(CarOfferModel.class));

        carOfferTable.setModel(carOfferModelListTableModel);
    }


    public void setCarSearchModel(CarSearchModel carSearchModel) {
        this.carSearchModel = carSearchModel;
    }

    public void setCarSearchRunnable(Runnable runnable) {
        AbstractAction searchAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = vehicleType.getSelectedItem();
                carSearchModel.setVehicleType(String.valueOf(selectedItem));
                carSearchModel.setPickupDate(LocalDateTime.parse(pickUpDate.getText().trim()));
                carSearchModel.setReturnDate(LocalDateTime.parse(returnDate.getText().trim()));
                runnable.run();
            }
        };
        searchAction.putValue(Action.NAME, "Search");
        searchButton.setAction(searchAction);
    }

    public Component getViewComponent() {
        return viewPanel;
    }
}
