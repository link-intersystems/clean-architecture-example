package com.link_intersystems.carrental.offer;

import com.link_intersystems.swing.list.ListModelSelection;
import com.link_intersystems.swing.selection.ListSelection;
import com.link_intersystems.swing.selection.SelectionProvider;
import com.link_intersystems.swing.selection.SelectionProviderSupport;
import com.link_intersystems.swing.table.DefaultListTableModel;
import com.link_intersystems.swing.table.beans.BeanTableElementSupport;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchResultView {

    private ListModel<CarOfferModel> carOfferListModel;

    private DefaultListTableModel<CarOfferModel> carOfferModelListTableModel = new DefaultListTableModel<>();
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

            ListSelection<CarOfferModel> newSelection = new ListSelection<>();
            newSelection.addAll(listModelSelection.toList());
            carOfferModelSelectionProvider.setSelection(newSelection);
        });
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {
        this.carOfferListModel = carOfferListModel;
        onCarOfferListModelChange(carOfferListModel);
    }

    private void onCarOfferListModelChange(ListModel<CarOfferModel> carOfferListModel) {
        carOfferModelListTableModel.setListModel(carOfferListModel);
        BeanTableElementSupport<CarOfferModel> beanTableElementSupport = BeanTableElementSupport.of(CarOfferModel.class);
        carOfferModelListTableModel.setTableElementMetaData(beanTableElementSupport);
        carOfferModelListTableModel.setTableElementCell(beanTableElementSupport);
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
