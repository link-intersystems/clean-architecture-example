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
import com.link_intersystems.carrental.management.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarUIConfig;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarView;
import com.link_intersystems.carrental.management.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.management.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.management.returnCar.ReturnCarUseCase;
import com.link_intersystems.carrental.management.returnCar.ui.ReturnCarFormController;
import com.link_intersystems.carrental.management.returnCar.ui.ReturnCarUIConfig;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.jdbc.JdbcTemplate;

import static java.util.Objects.*;

public class CarManagementViewConfig {

    private AOPConfig aopConfig;
    private JdbcTemplate jdbcTemplate;
    private MessageDialog messageDialog;
    private CarManagementView carManagementView;

    private CarBookedEventSubscriber carBookedEventSubscriber;

    public CarManagementViewConfig(AOPConfig aopConfig, JdbcTemplate jdbcTemplate, MessageDialog messageDialog) {
        this.aopConfig = requireNonNull(aopConfig);
        this.jdbcTemplate = requireNonNull(jdbcTemplate);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public CarManagementView getCarManagementView() {
        if (carManagementView == null) {
            ListCarBookingComponent listCarBookingComponent = new ListCarBookingComponent();
            ListBookingsUseCase listBookingUseCase = listCarBookingComponent.getListBookingsUseCase(jdbcTemplate);
            listBookingUseCase = aopConfig.applyAOP(listBookingUseCase);

            ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
            ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
            PickupUIConfig pickupUIConfig = new PickupUIConfig();

            PickupCarComponent pickupCarComponent = new PickupCarComponent();
            PickupCarUseCase pickupCarUseCase = pickupCarComponent.getPickupCarUseCase(jdbcTemplate);
            pickupCarUseCase = aopConfig.applyAOP(pickupCarUseCase);

            PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
            ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);


            ListPickupCarComponent listPickupCarComponent = new ListPickupCarComponent();
            ListPickupCarUseCase listPickupCarUseCase = listPickupCarComponent.getListPickupCarUseCase(jdbcTemplate);
            listPickupCarUseCase = aopConfig.applyAOP(listPickupCarUseCase);

            ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
            ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(listPickupCarUseCase);

            ReturnCarComponent returnCarComponent = new ReturnCarComponent();
            ReturnCarUseCase returnCarUseCase = returnCarComponent.getReturnUseCase(jdbcTemplate);
            returnCarUseCase = aopConfig.applyAOP(returnCarUseCase);

            GetPickupCarComponent getPickupCarComponent = new GetPickupCarComponent();
            GetPickupCarUseCase getPickupCarUseCase = getPickupCarComponent.getGetPickupCarUseCase(jdbcTemplate);
            getPickupCarUseCase = aopConfig.applyAOP(getPickupCarUseCase);

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
            CreateCarBookingComponent createCarBookingComponent = new CreateCarBookingComponent();
            CreateCarBookingUseCase createCarBookingUseCase = createCarBookingComponent.getCreateCarBookingUseCase(jdbcTemplate);
            createCarBookingUseCase = aopConfig.applyAOP(createCarBookingUseCase);

            carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
        }
        return carBookedEventSubscriber;
    }
}
