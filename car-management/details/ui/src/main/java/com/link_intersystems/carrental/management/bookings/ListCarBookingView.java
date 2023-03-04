package com.link_intersystems.carrental.management.bookings;

import com.link_intersystems.swing.table.DefaultListTableDescriptorModel;
import com.link_intersystems.swing.table.DefaultListTableModel;

import javax.swing.*;
import java.awt.*;

public class ListCarBookingView {

    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel actionPanel = new JPanel();

    private DefaultListTableDescriptorModel<CarBookingModel> tableDescriptorModel = new DefaultListTableDescriptorModel<>();
    private DefaultListTableModel<CarBookingModel> carBookingTableModel = new DefaultListTableModel<>();
    private JTable bookingsTable = new JTable();
    private JScrollPane bookingsTableScrollPane = new JScrollPane((bookingsTable));

    private JButton listCarBookingsButton = new JButton();

    public ListCarBookingView() {

        tableDescriptorModel.addColumnDescriptor("Booking Number", CarBookingModel::getBookingNumber);
        tableDescriptorModel.addColumnDescriptor("VIN", CarBookingModel::getVin);
        carBookingTableModel.setListTableDescriptorModel(tableDescriptorModel);

        bookingsTable.setModel(carBookingTableModel);

        panel.add(actionPanel, BorderLayout.EAST);
        panel.add(bookingsTableScrollPane, BorderLayout.CENTER);

        listCarBookingsButton.setVisible(false);

        actionPanel.add(listCarBookingsButton);

    }

    public void setListCarBookingModel(ListModel<CarBookingModel> carBookingListModel) {
        carBookingTableModel.setListModel(carBookingListModel);
    }

    public void setListCarBookingsAction(Action listCarBookingsAction) {
        listCarBookingsButton.setAction(listCarBookingsAction);
        listCarBookingsButton.setVisible(listCarBookingsAction != null);
    }

    public Component getViewComponent() {
        return panel;
    }
}
