package com.link_intersystems.carrental.offers.ui;

import com.link_intersystems.swing.list.ListModelSelection;
import com.link_intersystems.swing.selection.SelectionProvider;
import com.link_intersystems.swing.selection.SelectionProviderSupport;
import com.link_intersystems.swing.table.ListTableModel;
import com.link_intersystems.swing.table.beans.BeanListTableModelSupport;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchResultView {

    private JTable carOfferTable = new JTable();
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

        ListTableModel<CarOfferModel> carOfferModelListTableModel = new ListTableModel<>();

        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.setListTableModelSupport(BeanListTableModelSupport.of(CarOfferModel.class));
        listModelSelection.setListModel(carOfferListModel);
        carOfferTable.setModel(carOfferModelListTableModel);
    }

    public SelectionProvider<CarOfferModel> getSelectionProvider() {
        return carOfferModelSelectionProvider;
    }


    public Component getViewComponent() {
        return carOfferTableScrollPane;
    }
}
