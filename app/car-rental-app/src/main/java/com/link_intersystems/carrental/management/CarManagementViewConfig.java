package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.AOPConfig;
import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.carrental.management.booking.create.CarBookedEventSubscriber;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingComponent;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingUseCase;
import com.link_intersystems.carrental.management.booking.list.ListBookingsUseCase;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingComponent;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingController;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingUIConfig;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarUIConfig;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarView;
import com.link_intersystems.carrental.management.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarComponent;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarComponent;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarUseCase;
import com.link_intersystems.carrental.management.returnCar.ui.ReturnCarFormController;
import com.link_intersystems.carrental.management.returnCar.ui.ReturnCarUIConfig;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public class CarManagementViewConfig {

    private AOPConfig aopConfig;
    private JdbcTemplate jdbcTemplate;
    private MessageDialog messageDialog;
    private List<DomainEventSubscriber> domainEventSubscriberList = new ArrayList<>();
    private CarManagementView carManagementView;

    public CarManagementViewConfig(AOPConfig aopConfig, JdbcTemplate jdbcTemplate, MessageDialog messageDialog) {
        this.aopConfig = requireNonNull(aopConfig);
        this.jdbcTemplate = requireNonNull(jdbcTemplate);
        this.messageDialog = requireNonNull(messageDialog);
    }

    public List<DomainEventSubscriber> getDomainEventSubscriberList() {
        return domainEventSubscriberList;
    }

    public CarManagementView getCarManagementView() {
        if (carManagementView == null) {
            CreateCarBookingComponent createCarBookingComponent = new CreateCarBookingComponent();
            CreateCarBookingUseCase createCarBookingUseCase = createCarBookingComponent.getCreateCarBookingUseCase(jdbcTemplate);
            createCarBookingUseCase = aopConfig.applyAOP(createCarBookingUseCase);

            CarBookedEventSubscriber carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
            domainEventSubscriberList.add(carBookedEventSubscriber);

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
            ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(messageDialog, listPickupCarUseCase);

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
}
