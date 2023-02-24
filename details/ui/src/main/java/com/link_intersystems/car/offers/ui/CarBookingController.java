package com.link_intersystems.car.offers.ui;

import com.link_intersystems.car.booking.CarBookingUseCase;
import com.link_intersystems.swing.selection.Selection;
import com.link_intersystems.swing.selection.SelectionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CarBookingController extends AbstractAction {

    private CarBookingUseCase carBookingUseCase;

    private Selection<CarOfferModel> carOfferSelection = Selection.empty();

    private SelectionListener<CarOfferModel> carOfferModelSelectionListener = event -> {
        carOfferSelection = event.getSelection();
        setEnabled(!carOfferSelection.isEmpty());
    };

    public CarBookingController(CarBookingUseCase carBookingUseCase) {
        this.carBookingUseCase = carBookingUseCase;

        putValue(Action.NAME, "Book");
        setEnabled(false);
    }

    public SelectionListener<CarOfferModel> getSelectionListener() {
        return carOfferModelSelectionListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CarOfferModel carToBook = carOfferSelection.getFirstElement();

        JOptionPane.showMessageDialog(null, "Booking car " + carToBook.getId());
    }

}
