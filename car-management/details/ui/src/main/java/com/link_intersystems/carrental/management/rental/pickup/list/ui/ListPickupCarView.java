package com.link_intersystems.carrental.management.rental.pickup.list.ui;

import com.link_intersystems.carrental.swing.action.JActionToolBar;
import com.link_intersystems.swing.list.ListModelSelection;
import com.link_intersystems.swing.selection.SelectionListener;
import com.link_intersystems.swing.selection.SelectionProviderSupport;
import com.link_intersystems.swing.table.DefaultListTableDescriptorModel;
import com.link_intersystems.swing.table.DefaultListTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

public class ListPickupCarView {


    private JPanel panel = new JPanel(new BorderLayout());

    private JActionToolBar actionToolBar = new JActionToolBar();
    private DefaultListModel<Action> toolbarActions = new DefaultListModel<>();

    private DefaultListTableDescriptorModel<ListPickupCarModel> tableDescriptorModel = new DefaultListTableDescriptorModel<>();
    private DefaultListTableModel<ListPickupCarModel> pickupCarTableModel = new DefaultListTableModel<>();
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private JTable bookingsTable = new JTable();
    private JScrollPane bookingsTableScrollPane = new JScrollPane((bookingsTable));

    private SelectionProviderSupport<ListPickupCarModel> pickupCarSelectionSupport = new SelectionProviderSupport<>(this);


    public ListPickupCarView() {
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.addListSelectionListener(this::onSelectionChanged);

        bookingsTable.setSelectionModel(listSelectionModel);

        actionToolBar.setModel(toolbarActions);
        tableDescriptorModel.addColumnDescriptor("Booking Number", ListPickupCarModel::getBookingNumber);
        tableDescriptorModel.addColumnDescriptor("Pickup Date", ListPickupCarModel::getPickupDate);
        pickupCarTableModel.setListTableDescriptorModel(tableDescriptorModel);

        bookingsTable.setModel(pickupCarTableModel);

        panel.add(actionToolBar, BorderLayout.NORTH);
        panel.add(bookingsTableScrollPane, BorderLayout.CENTER);
    }

    private void onSelectionChanged(ListSelectionEvent e) {
        ListModelSelection<ListPickupCarModel> newSelection = new ListModelSelection<>(pickupCarTableModel.getListModel(), listSelectionModel);
        pickupCarSelectionSupport.setSelection(newSelection);
    }

    public void addSelectionChangedListener(SelectionListener<ListPickupCarModel> listener) {
        pickupCarSelectionSupport.addSelectionChangedListener(listener);
    }

    public void removeSelectionChangedListener(SelectionListener<ListPickupCarModel> listener) {
        pickupCarSelectionSupport.removeSelectionChangedListener(listener);
    }

    public void setListCarBookingModel(ListModel<ListPickupCarModel> carBookingListModel) {
        pickupCarTableModel.setListModel(carBookingListModel);
    }

    public void addToolbarAction(Action action) {
        toolbarActions.addElement(action);
    }

    public Component getViewComponent() {
        return panel;
    }
}
