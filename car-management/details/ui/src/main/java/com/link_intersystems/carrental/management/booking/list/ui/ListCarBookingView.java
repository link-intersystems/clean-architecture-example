package com.link_intersystems.carrental.management.booking.list.ui;

import com.link_intersystems.carrental.swing.action.JActionToolBar;
import com.link_intersystems.swing.list.ListModelSelection;
import com.link_intersystems.swing.selection.SelectionListener;
import com.link_intersystems.swing.selection.SelectionProviderSupport;
import com.link_intersystems.swing.table.DefaultListTableDescriptorModel;
import com.link_intersystems.swing.table.DefaultListTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ListCarBookingView {


    private JPanel panel = new JPanel(new BorderLayout());

    private JActionToolBar actionToolBar = new JActionToolBar();
    private DefaultListModel<Action> toolbarActions = new DefaultListModel<>();

    private DefaultListTableDescriptorModel<ListCarBookingModel> tableDescriptorModel = new DefaultListTableDescriptorModel<>();
    private DefaultListTableModel<ListCarBookingModel> carBookingTableModel = new DefaultListTableModel<>();
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private JTable bookingsTable = new JTable();
    private JScrollPane bookingsTableScrollPane = new JScrollPane((bookingsTable));

    private SelectionProviderSupport<ListCarBookingModel> listCarBookingModelSelectionProvider = new SelectionProviderSupport<>(this);


    public ListCarBookingView() {
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(this::onSelectionChanged);

        bookingsTable.setSelectionModel(listSelectionModel);

        actionToolBar.setModel(toolbarActions);
        tableDescriptorModel.addColumnDescriptor("Booking Number", ListCarBookingModel::getBookingNumber);
        tableDescriptorModel.addColumnDescriptor("VIN", ListCarBookingModel::getVin);
        tableDescriptorModel.addColumnDescriptor("Customer", this::getCustomerName);
        carBookingTableModel.setListTableDescriptorModel(tableDescriptorModel);

        bookingsTable.setModel(carBookingTableModel);

        panel.add(actionToolBar, BorderLayout.NORTH);
        panel.add(bookingsTableScrollPane, BorderLayout.CENTER);
    }

    private String getCustomerName(ListCarBookingModel listCarBookingModel) {
        CustomerModel customerModel = listCarBookingModel.getCustomerModel();
        return customerModel.getFirstname() + " " + customerModel.getLastname();
    }

    private void onSelectionChanged(ListSelectionEvent e) {
        ListModelSelection<ListCarBookingModel> newSelection = new ListModelSelection<>(carBookingTableModel.getListModel(), listSelectionModel);
        listCarBookingModelSelectionProvider.setSelection(newSelection);
    }

    public void addSelectionChangedListener(SelectionListener<ListCarBookingModel> listener) {
        listCarBookingModelSelectionProvider.addSelectionChangedListener(listener);
    }

    public void removeSelectionChangedListener(SelectionListener<ListCarBookingModel> listener) {
        listCarBookingModelSelectionProvider.removeSelectionChangedListener(listener);
    }

    public void setListCarBookingModel(ListModel<ListCarBookingModel> carBookingListModel) {
        carBookingTableModel.setListModel(carBookingListModel);
    }

    public void addListCarAction(Action action) {
        toolbarActions.addElement(action);
    }

    public Component getViewComponent() {
        return panel;
    }
}

