package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.swing.selection.ListSelectionProvider;
import com.link_intersystems.swing.selection.SelectionProvider;
import com.link_intersystems.swing.table.DefaultListTableModel;
import com.link_intersystems.swing.table.beans.BeanListTableDescriptorModel;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchResultView {


    private DefaultListTableModel<CarOfferModel> carOfferModelListTableModel = new DefaultListTableModel<>();
    private JTable carOfferTable = new JTable();
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private JScrollPane carOfferTableScrollPane = new JScrollPane(carOfferTable);

    private ListSelectionProvider<CarOfferModel> listSelectionProvider = new ListSelectionProvider<>(this);

    CarOfferSearchResultView() {
        BeanListTableDescriptorModel<CarOfferModel> beanTableElementSupport = BeanListTableDescriptorModel.of(CarOfferModel.class);
        carOfferModelListTableModel.setListTableDescriptorModel(beanTableElementSupport);
        carOfferTable.setModel(carOfferModelListTableModel);

        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carOfferTable.setSelectionModel(listSelectionModel);

        listSelectionProvider.setListSelectionModel(listSelectionModel);
    }

    JTable getCarOfferTable() {
        return carOfferTable;
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {
        listSelectionProvider.setListModel(carOfferListModel);
        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.fireTableStructureChanged();
    }

    public SelectionProvider<CarOfferModel> getSelectionProvider() {
        return listSelectionProvider;
    }

    public Component getViewComponent() {
        return carOfferTableScrollPane;
    }
}
