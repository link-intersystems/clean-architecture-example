package com.link_intersystems.carrental.offer.ui;

import com.link_intersystems.carrental.ui.ApplicationModel;
import com.link_intersystems.carrental.ui.View;
import com.link_intersystems.swing.selection.SelectionProvider;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CarOfferView implements View {


    private CarOfferSearchArgumentsView carOfferSearchArgumentsView = new CarOfferSearchArgumentsView();
    private CarOfferSearchResultView carOfferSearchResultView = new CarOfferSearchResultView();
    private CarOfferBookingView carOfferBookingView = new CarOfferBookingView();

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

    @Override
    public String getTitle() {
        return "Car Booking";
    }

    @Override
    public Component createComponent(ApplicationModel applicationModel) {
        JPanel viewPanel = new JPanel();

        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(carOfferSearchArgumentsView.getViewComponent(), BorderLayout.NORTH);
        viewPanel.add(carOfferSearchResultView.getViewComponent(), BorderLayout.CENTER);
        viewPanel.add(carOfferBookingView.getViewComponent(), BorderLayout.EAST);

        return viewPanel;
    }

    @Override
    public List<String> getRequiredRoles() {
        return Arrays.asList("CUSTOMER");
    }
}
