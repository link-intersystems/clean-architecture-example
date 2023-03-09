package com.link_intersystems.carrental;

import com.link_intersystems.carrental.booking.CarBookingController;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.booking.CarBookingUseCaseManualConfig;
import com.link_intersystems.carrental.management.CarManagementUIConfig;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingManualConfig;
import com.link_intersystems.carrental.management.booking.list.ListBookingsUseCase;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingManualConfig;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingController;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingModel;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingUIConfig;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCaseManualConfig;
import com.link_intersystems.carrental.management.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.offer.*;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.carrental.ui.CarRentalMainUIConfig;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.function.Consumer;

public class CarRentalManualConfig {

    private H2DataSourceConfig h2DataSourceConfig = new H2DataSourceConfig();
    private Consumer<CarRentalMainFrame> mainFrameSetter;

    public CarRentalMainFrame createMainFrame() {

        CarRentalMainUIConfig carRentalMainUIConfig = new CarRentalMainUIConfig();
        MessageDialog messageDialog = carRentalMainUIConfig.getMessageDialog(s -> mainFrameSetter = s);

        CarOfferView carOfferView = createCarOfferView(messageDialog);
        CarManagementView carManagementView = createCarManagementView(messageDialog);

        CarRentalMainFrame mainFrame = carRentalMainUIConfig.getMainFrame(carOfferView, carManagementView);
        mainFrameSetter.accept(mainFrame);
        return mainFrame;
    }

    private CarOfferView createCarOfferView(MessageDialog messageDialog) {
        DataSource rentalDataSource = h2DataSourceConfig.getCarRentalDataSource();

        CarBookingUseCaseManualConfig carBookingUseCaseManualConfig = new CarBookingUseCaseManualConfig();
        CarBookingUseCase carBookingUseCase = carBookingUseCaseManualConfig.getCarBookingUseCase(rentalDataSource);

        CarOfferUseCaseManualConfig carOfferUseCaseManualConfig = new CarOfferUseCaseManualConfig();
        CarOfferUseCase carOfferUseCase = carOfferUseCaseManualConfig.getCarOfferUseCase(rentalDataSource);

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);

        CarOfferController carOfferController = carOfferUIConfig.getCarOfferController(carOfferUseCase, messageDialog);
        CarBookingController carBookingController = carOfferUIConfig.getCarBookingController(carBookingUseCase, carOfferController);

        return carOfferUIConfig.getCarOfferView(carOfferController, carBookingController);
    }

    private CarManagementView createCarManagementView(MessageDialog messageDialog) {
        DataSource managementDataSource = h2DataSourceConfig.getManagementDataSource();

        CreateCarBookingManualConfig carBookingManualConfig = new CreateCarBookingManualConfig();
        carBookingManualConfig.getDomainEventSubscriber(managementDataSource);

        ListCarBookingManualConfig listCarBookingManualConfig = new ListCarBookingManualConfig();
        ListBookingsUseCase listBookingUseCase = listCarBookingManualConfig.getListBookingUseCase(managementDataSource);


        PickupCarUseCaseManualConfig pickupCarUseCaseManualConfig = new PickupCarUseCaseManualConfig();
        PickupCarUseCase pickupCarUseCase = pickupCarUseCaseManualConfig.getPickupCarUseCase(managementDataSource);

        ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
        ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
        PickupUIConfig pickupUIConfig = new PickupUIConfig();
        PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
        ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);

        CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
        return carManagementUIConfig.getCarManagementView(listCarBookingView);
    }

    private ListCarBookingView createListCarBookingView(ListBookingsUseCase listBookingsUseCase, PickupCarUseCase pickupCarUseCase, MessageDialog messageDialog) {
        ListCarBookingController listCarBookingController = new ListCarBookingController(listBookingsUseCase);
        PickupCarController pickupCarController = new PickupCarController(pickupCarUseCase, messageDialog);

        ListCarBookingView listCarBoookingView = new ListCarBookingView();
        ListModel<ListCarBookingModel> listCarBookingModel = listCarBookingController.getCarBookingListModel();
        listCarBoookingView.setListCarBookingModel(listCarBookingModel);
        listCarBoookingView.addListCarAction(listCarBookingController);
        listCarBoookingView.addListCarAction(pickupCarController);
        return listCarBoookingView;
    }
}
