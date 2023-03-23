package com.link_intersystems.carrental;

import com.link_intersystems.carrental.booking.CarBookingComponent;
import com.link_intersystems.carrental.booking.CarBookingController;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.management.CarManagementUIConfig;
import com.link_intersystems.carrental.management.CarManagementView;
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
import com.link_intersystems.carrental.offer.*;
import com.link_intersystems.carrental.swing.notification.DefaultMessageDialog;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.carrental.ui.CarRentalMainUIConfig;
import com.link_intersystems.jdbc.JdbcTemplate;
import com.link_intersystems.jdbc.tx.LocalTransactionManager;
import com.link_intersystems.jdbc.tx.TransactionAwareDataSource;
import com.link_intersystems.jdbc.tx.TransactionManager;
import com.link_intersystems.jdbc.tx.TransactionProxy;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CarRentalMain {

    private H2DataSourceConfig h2DataSourceConfig = new H2DataSourceConfig();
    private List<DomainEventSubscriber> domainEventSubscriberList = new ArrayList<>();
    private DataSource dataSource;
    private TransactionManager transactionManager;

    public void initDataSource() {
        DataSource dataSource = h2DataSourceConfig.getDataSource();
        transactionManager = new LocalTransactionManager(dataSource);
        this.dataSource = new TransactionAwareDataSource(dataSource);
    }

    private <T> T applyAOP(T bean) {
        return TransactionProxy.create(bean, transactionManager);
    }

    public CarRentalMainFrame createMainFrame() {
        CarRentalMainUIConfig carRentalMainUIConfig = new CarRentalMainUIConfig();
        DefaultMessageDialog messageDialog = carRentalMainUIConfig.getMessageDialog();

        CarManagementView carManagementView = createCarManagementView(dataSource, messageDialog);
        CarOfferView carOfferView = createCarOfferView(dataSource, messageDialog);

        CarRentalMainFrame mainFrame = carRentalMainUIConfig.getMainFrame(carOfferView, carManagementView);
        messageDialog.setParentComponent(mainFrame.getComponent());
        return mainFrame;
    }

    private CarOfferView createCarOfferView(DataSource dataSource, MessageDialog messageDialog) {

        JdbcTemplate rentalJdbcTemplate = h2DataSourceConfig.getCarRentalJdbcTemplate(dataSource);

        CarBookingComponent carBookingComponent = new CarBookingComponent();
        CarBookingUseCase carBookingUseCase = carBookingComponent.getCarBookingUseCase(rentalJdbcTemplate, domainEventSubscriberList);
        carBookingUseCase = applyAOP(carBookingUseCase);

        CarOfferComponent carOfferComponent = new CarOfferComponent();
        CarOfferUseCase carOfferUseCase = carOfferComponent.createCarOfferUseCase(rentalJdbcTemplate);
        carOfferUseCase = applyAOP(carOfferUseCase);

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);

        CarOfferController carOfferController = carOfferUIConfig.getCarOfferController(carOfferUseCase, messageDialog);
        CarBookingController carBookingController = carOfferUIConfig.getCarBookingController(carBookingUseCase, carOfferController);

        return carOfferUIConfig.getCarOfferView(carOfferController, carBookingController);
    }

    private CarManagementView createCarManagementView(DataSource dataSource, MessageDialog messageDialog) {
        JdbcTemplate managementJdbcTemplate = h2DataSourceConfig.getManagementJdbcTemplate(dataSource);

        CreateCarBookingComponent createCarBookingComponent = new CreateCarBookingComponent();
        CreateCarBookingUseCase createCarBookingUseCase = createCarBookingComponent.getCreateCarBookingUseCase(managementJdbcTemplate);
        createCarBookingUseCase = applyAOP(createCarBookingUseCase);

        CarBookedEventSubscriber carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
        domainEventSubscriberList.add(carBookedEventSubscriber);

        ListCarBookingComponent listCarBookingComponent = new ListCarBookingComponent();
        ListBookingsUseCase listBookingUseCase = listCarBookingComponent.getListBookingsUseCase(managementJdbcTemplate);
        listBookingUseCase = applyAOP(listBookingUseCase);

        ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
        ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
        PickupUIConfig pickupUIConfig = new PickupUIConfig();

        PickupCarComponent pickupCarComponent = new PickupCarComponent();
        PickupCarUseCase pickupCarUseCase = pickupCarComponent.getPickupCarUseCase(managementJdbcTemplate);
        pickupCarUseCase = applyAOP(pickupCarUseCase);

        PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
        ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);


        ListPickupCarComponent listPickupCarComponent = new ListPickupCarComponent();
        ListPickupCarUseCase listPickupCarUseCase = listPickupCarComponent.getListPickupCarUseCase(managementJdbcTemplate);
        listPickupCarUseCase = applyAOP(listPickupCarUseCase);

        ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
        ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(messageDialog, listPickupCarUseCase);

        ReturnCarComponent returnCarComponent = new ReturnCarComponent();
        ReturnCarUseCase returnCarUseCase = returnCarComponent.getReturnUseCase(managementJdbcTemplate);
        returnCarUseCase = applyAOP(returnCarUseCase);

        GetPickupCarComponent getPickupCarComponent = new GetPickupCarComponent();
        GetPickupCarUseCase getPickupCarUseCase = getPickupCarComponent.getGetPickupCarUseCase(managementJdbcTemplate);
        getPickupCarUseCase = applyAOP(getPickupCarUseCase);

        ReturnCarUIConfig returnCarUIConfig = new ReturnCarUIConfig();
        ReturnCarFormController returnCarFormController = returnCarUIConfig.getReturnCarController(getPickupCarUseCase, messageDialog, listPickupCarController, returnCarUseCase);

        ListPickupCarView listPickupCarView = listPickupCarUIConfig.getListPickupCarView(listPickupCarController, returnCarFormController);
        CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
        return carManagementUIConfig.getCarManagementView(listCarBookingView, listPickupCarView);
    }


}
