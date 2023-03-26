package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.swing.selection.SelectionProvider;

import javax.swing.*;
import java.awt.*;

public class CarOfferView {

    private JPanel viewPanel = new JPanel();

    private CarOfferSearchArgumentsView carOfferSearchArgumentsView = new CarOfferSearchArgumentsView();
    private CarOfferSearchResultView carOfferSearchResultView = new CarOfferSearchResultView();
    private CarOfferBookingView carOfferBookingView = new CarOfferBookingView();

    public CarOfferView() {

        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(carOfferSearchArgumentsView.getViewComponent(), BorderLayout.NORTH);
        viewPanel.add(carOfferSearchResultView.getViewComponent(), BorderLayout.CENTER);
        viewPanel.add(carOfferBookingView.getViewComponent(), BorderLayout.EAST);
    }

    public SelectionProvider<CarOfferModel> getSelectionProvider() {
        return carOfferSearchResultView.getSelectionProvider();
    }

    public void setCarSearchModel(CarSearchModel carSearchModel) {
        carOfferSearchArgumentsView.setCarSearchModel(carSearchModel);
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {
        carOfferSearchResultView.setCarOfferListModel(carOfferListModel);
    }

    public void setCarSearchAction(Action action) {
        carOfferSearchArgumentsView.setCarBookAction(action);
    }

    public void setBookCarAction(Action action) {
        carOfferBookingView.setBookCarAction(action);
    }

    public Component getViewComponent() {
        return viewPanel;
    }

}
