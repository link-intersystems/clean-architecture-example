package com.link_intersystems.car.offers.ui;

import javax.swing.*;
import java.awt.*;

public class CarOfferView {

    private JPanel viewPanel = new JPanel();

    private CarOfferSearchArgumentsView carOfferSearchArgumentsView = new CarOfferSearchArgumentsView();
    private CarOfferSearchResultView carOfferSearchResultView = new CarOfferSearchResultView();

    public CarOfferView() {

        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(carOfferSearchArgumentsView.getViewComponent(), BorderLayout.NORTH);
        viewPanel.add(carOfferSearchResultView.getViewComponent(), BorderLayout.CENTER);
    }

    public void setCarSearchModel(CarSearchModel carSearchModel) {
        carOfferSearchArgumentsView.setCarSearchModel(carSearchModel);
    }

    public void setCarOfferListModel(ListModel<CarOfferModel> carOfferListModel) {
        carOfferSearchResultView.setCarOfferListModel(carOfferListModel);
    }


    public void setCarSearchRunnable(Runnable runnable) {
        carOfferSearchArgumentsView.setSearchRunnable(runnable);
    }

    public Component getViewComponent() {
        return viewPanel;
    }
}
