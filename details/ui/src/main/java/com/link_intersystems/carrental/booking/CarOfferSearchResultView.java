package com.link_intersystems.carrental.booking;

import com.link_intersystems.swing.list.ListModelSelection;
import com.link_intersystems.swing.selection.SelectionProvider;
import com.link_intersystems.swing.selection.SelectionProviderSupport;
import com.link_intersystems.swing.table.ListTableModel;
import com.link_intersystems.swing.table.beans.BeanListTableModelSupport;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;

class CarOfferSearchResultView {

    private ListModel<CarOfferModel> carOfferListModel;

    private class ListDataAdapter implements ListDataListener {

        @Override
        public void intervalAdded(ListDataEvent e) {
            listSelectionModel.clearSelection();
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            listSelectionModel.clearSelection();
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            listSelectionModel.clearSelection();
        }
    }

    private ListDataListener listDataListener = new ListDataAdapter();

    private ListTableModel<CarOfferModel> carOfferModelListTableModel = new ListTableModel<>();
    private JTable carOfferTable = new JTable(carOfferModelListTableModel);
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private ListModelSelection<CarOfferModel> listModelSelection = new ListModelSelection<>();
    private JScrollPane carOfferTableScrollPane = new JScrollPane(carOfferTable);

    private SelectionProviderSupport<CarOfferModel> carOfferModelSelectionProvider = new SelectionProviderSupport<>(this);

    CarOfferSearchResultView() {
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carOfferTable.setSelectionModel(listSelectionModel);
        listModelSelection.setSelectionModel(listSelectionModel);

        listSelectionModel.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            ListSelectionModel listSelectionModel = listModelSelection.getListSelectionModel();
            ListModel<CarOfferModel> listModel = listModelSelection.getListModel();
            ListModelSelection<CarOfferModel> newSelection = new ListModelSelection<>(listModel, listSelectionModel);
            carOfferModelSelectionProvider.setSelection(newSelection);
        });
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {
        if (this.carOfferListModel != null) {
            this.carOfferListModel.removeListDataListener(listDataListener);
        }

        this.carOfferListModel = carOfferListModel;

        if (this.carOfferListModel != null) {
            this.carOfferListModel.addListDataListener(listDataListener);
        }

        onCarOfferListModelChange(carOfferListModel);
    }

    private void onCarOfferListModelChange(ListModel<CarOfferModel> carOfferListModel) {
        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.setListTableModelSupport(BeanListTableModelSupport.of(CarOfferModel.class));
        listModelSelection.setListModel(carOfferListModel);
        carOfferTable.setModel(carOfferModelListTableModel);
        carOfferModelListTableModel.fireTableStructureChanged();
    }

    public SelectionProvider<CarOfferModel> getSelectionProvider() {
        return carOfferModelSelectionProvider;
    }

    public Component getViewComponent() {
        return carOfferTableScrollPane;
    }
}
