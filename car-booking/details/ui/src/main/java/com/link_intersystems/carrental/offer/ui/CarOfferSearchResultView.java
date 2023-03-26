package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.swing.list.ListModelSelection;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionProvider;
import com.link_intersystems.swing.selection.SelectionProviderSupport;
import com.link_intersystems.swing.table.DefaultListTableModel;
import com.link_intersystems.swing.table.beans.BeanListTableDescriptorModel;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchResultView {

    private ListModel<CarOfferModel> carOfferListModel;

    private DefaultListTableModel<CarOfferModel> carOfferModelListTableModel = new DefaultListTableModel<>();
    private JTable carOfferTable = new JTable();
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private JScrollPane carOfferTableScrollPane = new JScrollPane(carOfferTable);

    private SelectionProviderSupport<CarOfferModel> carOfferModelSelectionProvider = new SelectionProviderSupport<>(this);

    CarOfferSearchResultView() {
        BeanListTableDescriptorModel<CarOfferModel> beanTableElementSupport = BeanListTableDescriptorModel.of(CarOfferModel.class);
        carOfferModelListTableModel.setListTableDescriptorModel(beanTableElementSupport);
        carOfferTable.setModel(carOfferModelListTableModel);

        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carOfferTable.setSelectionModel(listSelectionModel);

        listSelectionModel.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            Selection<CarOfferModel> newSelection = new ListModelSelection<>(carOfferListModel, listSelectionModel);
            carOfferModelSelectionProvider.setSelection(newSelection);
        });
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {
        this.carOfferListModel = carOfferListModel;
        onCarOfferListModelChange(carOfferListModel);
    }

    private void onCarOfferListModelChange(ListModel<CarOfferModel> carOfferListModel) {
        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.fireTableStructureChanged();
    }

    public SelectionProvider<CarOfferModel> getSelectionProvider() {
        return carOfferModelSelectionProvider;
    }

    public Component getViewComponent() {
        return carOfferTableScrollPane;
    }
}
