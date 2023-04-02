package com.link_intersystems.carrental.offer.ui;

import javax.swing.*;

public class CarOfferSearchResultViewControl {
    private CarOfferSearchResultView carOfferSearchResultView;

    public CarOfferSearchResultViewControl(CarOfferSearchResultView carOfferSearchResultView) {
        this.carOfferSearchResultView = carOfferSearchResultView;
    }

    public String getValueAt(int row, int column) {
        JTable carOfferTable = carOfferSearchResultView.getCarOfferTable();
        Object valueAt = carOfferTable.getValueAt(row, column);
        return String.valueOf(valueAt);
    }

    public void select(int row) {
        JTable carOfferTable = carOfferSearchResultView.getCarOfferTable();
        carOfferTable.setRowSelectionInterval(row, row);
    }
}
