package com.link_intersystems.car.offers.ui;

import com.link_intersystems.swing.table.beans.BeanListTableModelSupport;
import com.link_intersystems.swing.table.ListTableModel;

import javax.swing.*;
import java.awt.*;

class CarOfferSearchResultView {

    private JTable carOfferTable = new JTable();
    private JScrollPane carOfferTableScrollPane = new JScrollPane(carOfferTable);

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {

        ListTableModel<CarOfferModel> carOfferModelListTableModel = new ListTableModel<>();

        carOfferModelListTableModel.setListModel(carOfferListModel);
        carOfferModelListTableModel.setListTableModelSupport(BeanListTableModelSupport.of(CarOfferModel.class));

        carOfferTable.setModel(carOfferModelListTableModel);
    }


    public Component getViewComponent() {
        return carOfferTableScrollPane;
    }
}
