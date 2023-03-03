package com.link_intersystems.carrental.offer;

import com.link_intersystems.swing.notification.MessageDialog;
import com.link_intersystems.carrental.booking.*;

import java.time.LocalDateTime;

public class CarOfferConfig {

    public CarOfferUseCase getCarOfferUseCase(CarOfferRepository carOfferRepository) {
        return new CarOfferInteractor(carOfferRepository);
    }

    public CarSearchModel getCarSearchModel() {
        CarSearchModel carSearchModel = new CarSearchModel();

        carSearchModel.getVehicleType().setValue("MICRO");
        carSearchModel.getPickupDate().setValue(LocalDateTime.now().plusDays(1).toString());
        carSearchModel.getReturnDate().setValue(LocalDateTime.now().plusDays(2).toString());

        return carSearchModel;
    }

    public CarOfferView getCarOfferView(CarOfferController carOfferController, CarBookingController carBookingController) {
        CarOfferView carOfferView = new CarOfferView();
        carOfferView.setCarOfferListModel(carOfferController.getCarOfferListModel());
        carOfferView.setCarSearchModel(carOfferController.getCarSearchModel());
        carOfferView.setCarSearchAction(carOfferController);
        carOfferView.setBookCarAction(carBookingController);
        carOfferView.getSelectionProvider().addSelectionChangedListener(carBookingController.getSelectionListener());
        return carOfferView;
    }

    public CarBookingController getCarBookingController(CarSearchModel carSearchModel, MessageDialog messageDialog, CarBookingUseCase carBookingUseCase, CarOfferController carOfferController) {
        CarBookingController carBookingController = new CarBookingController(carBookingUseCase, carSearchModel, messageDialog);
        carBookingController.setOnDoneActionListener(carOfferController);
        return carBookingController;
    }

    public CarOfferController getCarOfferController(CarSearchModel carSearchModel, CarOfferUseCase carOfferUseCase, MessageDialog messageDialog) {
        CarOfferController carOfferController = new CarOfferController(carOfferUseCase, carSearchModel);
        carOfferController.setMessageDialog(messageDialog);
        return carOfferController;
    }

}
