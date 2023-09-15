package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.management.booking.*;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingController;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingUIConfig;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.rental.*;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarUIConfig;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarView;
import com.link_intersystems.carrental.management.rental.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.rental.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.management.rental.returnCar.ui.ReturnCarFormController;
import com.link_intersystems.carrental.management.rental.returnCar.ui.ReturnCarUIConfig;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

import static java.util.Objects.*;

public class CarManagementViewConfig {

    private final ComponentsConfig componentsConfig;
    private final MessageDialog messageDialog;
    private final GetPickupCarUseCase getPickupCarUseCase;
    private final ReturnCarUseCase returnCarUseCase;
    private final ListPickupCarUseCase listPickupCarUseCase;
    private final PickupCarUseCase pickupCarUseCase;
    private final ListBookingsUseCase listBookingUseCase;
    private CarManagementView carManagementView;

    private CarBookedEventSubscriber carBookedEventSubscriber;
    private CarRentalEventSubscriber carRentalEventSubscriber;
    private ListPickupCarView listPickupCarView;
    private ListCarBookingView listCarBookingView;


    public CarManagementViewConfig(ComponentsConfig componentsConfig, DomainEventBus domainEventBus, MessageDialog messageDialog) {
        this.componentsConfig = requireNonNull(componentsConfig);
        this.messageDialog = requireNonNull(messageDialog);

        ManagementCarBookingComponent managementBookingComponent = componentsConfig.getManagementCarBookingComponent();
        listBookingUseCase = managementBookingComponent.getListBookingsUseCase();

        ManagementRentalComponent managementRentalComponent = componentsConfig.getManagementRentalComponent();
        pickupCarUseCase = managementRentalComponent.getPickupCarUseCase();
        listPickupCarUseCase = managementRentalComponent.getListPickupCarUseCase();
        returnCarUseCase = managementRentalComponent.getReturnUseCase();
        getPickupCarUseCase = managementRentalComponent.getGetPickupCarUseCase();

        domainEventBus.addSubscribers(getCarRentalEventSubscriber());
        domainEventBus.addSubscribers(getCarBookedEventSubscriber());


    }

    public CarManagementView getCarManagementView() {
        if (carManagementView == null) {

            ListCarBookingView listCarBookingView = getListCarBookingView();
            ListPickupCarView listPickupCarView = getListPickupCarView();
            CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
            carManagementView = carManagementUIConfig.getCarManagementView(listCarBookingView, listPickupCarView);
        }
        return carManagementView;
    }

    private ListCarBookingView getListCarBookingView() {
        if (listCarBookingView == null) {
            ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
            ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
            PickupUIConfig pickupUIConfig = new PickupUIConfig();
            PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
            listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);
        }
        return listCarBookingView;
    }

    private ListPickupCarView getListPickupCarView() {
        if (listPickupCarView == null) {
            ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
            ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(listPickupCarUseCase);

            ReturnCarUIConfig returnCarUIConfig = new ReturnCarUIConfig();
            ReturnCarFormController returnCarFormController = returnCarUIConfig.getReturnCarController(getPickupCarUseCase, messageDialog, listPickupCarController, returnCarUseCase);

            listPickupCarView = listPickupCarUIConfig.getListPickupCarView(listPickupCarController, returnCarFormController);
        }
        return listPickupCarView;
    }

    private DomainEventSubscriber getCarBookedEventSubscriber() {
        if (carBookedEventSubscriber == null) {
            ManagementCarBookingComponent managementCarBookingComponent = componentsConfig.getManagementCarBookingComponent();
            CreateCarBookingUseCase createCarBookingUseCase = managementCarBookingComponent.getCreateCarBookingUseCase();
            carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
        }
        return carBookedEventSubscriber;
    }

    private DomainEventSubscriber getCarRentalEventSubscriber() {
        if (carRentalEventSubscriber == null) {
            ManagementCarBookingComponent managementCarBookingComponent = componentsConfig.getManagementCarBookingComponent();
            UpdateCarBookingRentalUseCase updateCarBookingRentalUseCase = managementCarBookingComponent.getUpdateCarBookingRentalUseCase();
            carRentalEventSubscriber = new CarRentalEventSubscriber(updateCarBookingRentalUseCase);
        }
        return carRentalEventSubscriber;
    }
}
