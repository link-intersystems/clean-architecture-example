package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.main.AOPConfig;
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

import javax.sql.DataSource;

import static java.util.Objects.*;

public class CarManagementViewConfig {

    private AOPConfig aopConfig;
    private DataSource dataSource;
    private MessageDialog messageDialog;
    private CarManagementView carManagementView;

    private CarBookedEventSubscriber carBookedEventSubscriber;

    public CarManagementViewConfig(AOPConfig aopConfig, DataSource dataSource, MessageDialog messageDialog) {
        this.aopConfig = requireNonNull(aopConfig);
        this.dataSource = requireNonNull(dataSource);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarManagementView getCarManagementView() {
        if (carManagementView == null) {
            ListCarBookingComponent listCarBookingComponent = new ListCarBookingComponent(dataSource);
            ListBookingsUseCase listBookingUseCase = listCarBookingComponent.getListBookingsUseCase();
            listBookingUseCase = aopConfig.applyAOP(ListBookingsUseCase.class, listBookingUseCase);

            ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
            ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
            PickupUIConfig pickupUIConfig = new PickupUIConfig();

            PickupCarComponent pickupCarComponent = new PickupCarComponent(dataSource);
            PickupCarUseCase pickupCarUseCase = pickupCarComponent.getPickupCarUseCase();
            pickupCarUseCase = aopConfig.applyAOP(PickupCarUseCase.class, pickupCarUseCase);

            PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
            ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);


            ListPickupCarComponent listPickupCarComponent = new ListPickupCarComponent(dataSource);
            ListPickupCarUseCase listPickupCarUseCase = listPickupCarComponent.getListPickupCarUseCase();
            listPickupCarUseCase = aopConfig.applyAOP(ListPickupCarUseCase.class, listPickupCarUseCase);

            ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
            ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(listPickupCarUseCase);

            ReturnCarComponent returnCarComponent = new ReturnCarComponent(dataSource);
            ReturnCarUseCase returnCarUseCase = returnCarComponent.getReturnUseCase();
            returnCarUseCase = aopConfig.applyAOP(ReturnCarUseCase.class, returnCarUseCase);

            GetPickupCarComponent getPickupCarComponent = new GetPickupCarComponent(dataSource);
            GetPickupCarUseCase getPickupCarUseCase = getPickupCarComponent.getGetPickupCarUseCase();
            getPickupCarUseCase = aopConfig.applyAOP(GetPickupCarUseCase.class, getPickupCarUseCase);

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
            CreateCarBookingComponent createCarBookingComponent = new CreateCarBookingComponent(dataSource);
            CreateCarBookingUseCase createCarBookingUseCase = createCarBookingComponent.getCreateCarBookingUseCase();
            createCarBookingUseCase = aopConfig.applyAOP(CreateCarBookingUseCase.class, createCarBookingUseCase);

            carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
        }
        return carBookedEventSubscriber;
    }
}
