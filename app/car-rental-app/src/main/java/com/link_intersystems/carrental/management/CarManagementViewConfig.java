package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.components.ComponentsConfig;
import com.link_intersystems.carrental.management.booking.create.CarBookedEventSubscriber;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingUseCase;
import com.link_intersystems.carrental.management.booking.list.ListBookingsUseCase;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingController;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingUIConfig;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarUIConfig;
import com.link_intersystems.carrental.management.rental.pickup.list.ui.ListPickupCarView;
import com.link_intersystems.carrental.management.rental.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.rental.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarUseCase;
import com.link_intersystems.carrental.management.rental.returnCar.ui.ReturnCarFormController;
import com.link_intersystems.carrental.management.rental.returnCar.ui.ReturnCarUIConfig;
import com.link_intersystems.carrental.swing.notification.MessageDialog;

import static java.util.Objects.*;

public class CarManagementViewConfig {

    private final ComponentsConfig componentsConfig;
    private final MessageDialog messageDialog;
    private CarManagementView carManagementView;

    private CarBookedEventSubscriber carBookedEventSubscriber;


    public CarManagementViewConfig(ComponentsConfig componentsConfig, MessageDialog messageDialog) {
        this.componentsConfig = requireNonNull(componentsConfig);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarManagementView getCarManagementView() {
        if (carManagementView == null) {
            ListCarBookingComponent jdbcListCarBookingComponent = componentsConfig.getListCarBookingComponent();
            ListBookingsUseCase listBookingUseCase = jdbcListCarBookingComponent.getListBookingsUseCase();

            ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
            ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
            PickupUIConfig pickupUIConfig = new PickupUIConfig();

            PickupCarComponent pickupCarComponent = componentsConfig.getPickupCarComponent();
            PickupCarUseCase pickupCarUseCase = pickupCarComponent.getPickupCarUseCase();

            PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
            ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);


            ListPickupCarComponent listPickupCarComponent = componentsConfig.getListPickupCarComponent();
            ListPickupCarUseCase listPickupCarUseCase = listPickupCarComponent.getListPickupCarUseCase();

            ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
            ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(listPickupCarUseCase);

            ReturnCarComponent returnCarComponent = componentsConfig.getReturnCarComponent();
            ReturnCarUseCase returnCarUseCase = returnCarComponent.getReturnUseCase();

            GetPickupCarComponent getPickupCarComponent = componentsConfig.getGetPickupCarComponent();
            GetPickupCarUseCase getPickupCarUseCase = getPickupCarComponent.getGetPickupCarUseCase();

            ReturnCarUIConfig returnCarUIConfig = new ReturnCarUIConfig();
            ReturnCarFormController returnCarFormController = returnCarUIConfig.getReturnCarController(getPickupCarUseCase, messageDialog, listPickupCarController, returnCarUseCase);

            ListPickupCarView listPickupCarView = listPickupCarUIConfig.getListPickupCarView(listPickupCarController, returnCarFormController);
            CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
            carManagementView = carManagementUIConfig.getCarManagementView(listCarBookingView, listPickupCarView);
        }
        return carManagementView;
    }

    public DomainEventSubscriber getCarBookedEventSubscriber() {
        if (carBookedEventSubscriber == null) {
            CreateCarBookingComponent createCarBookingComponent = componentsConfig.getCreateCarBookingComponent();
            CreateCarBookingUseCase createCarBookingUseCase = createCarBookingComponent.getCreateCarBookingUseCase();
            carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
        }
        return carBookedEventSubscriber;
    }
}
