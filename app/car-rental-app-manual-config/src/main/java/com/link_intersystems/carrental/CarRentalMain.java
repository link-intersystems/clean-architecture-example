package com.link_intersystems.carrental;

import com.link_intersystems.carrental.booking.CarBookingController;
import com.link_intersystems.carrental.booking.CarBookingUseCase;
import com.link_intersystems.carrental.booking.CarBookingUseCaseMain;
import com.link_intersystems.carrental.management.CarManagementUIConfig;
import com.link_intersystems.carrental.management.CarManagementView;
import com.link_intersystems.carrental.management.booking.create.CreateCarBookingMain;
import com.link_intersystems.carrental.management.booking.list.ListBookingsUseCase;
import com.link_intersystems.carrental.management.booking.list.ListCarBookingMain;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingController;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingUIConfig;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCaseMain;
import com.link_intersystems.carrental.management.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.offer.*;
import com.link_intersystems.carrental.swing.notification.MessageDialog;
import com.link_intersystems.carrental.ui.CarRentalMainFrame;
import com.link_intersystems.carrental.ui.CarRentalMainUIConfig;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CarRentalMain {

    private H2DataSourceConfig h2DataSourceConfig = new H2DataSourceConfig();
    private Consumer<CarRentalMainFrame> mainFrameSetter;
    private List<DomainEventSubscriber> domainEventSubscriberList = new ArrayList<>();

    public CarRentalMainFrame createMainFrame() {

        CarRentalMainUIConfig carRentalMainUIConfig = new CarRentalMainUIConfig();
        MessageDialog messageDialog = carRentalMainUIConfig.getMessageDialog(s -> mainFrameSetter = s);

        CarManagementView carManagementView = createCarManagementView(messageDialog);
        CarOfferView carOfferView = createCarOfferView(messageDialog);

        CarRentalMainFrame mainFrame = carRentalMainUIConfig.getMainFrame(carOfferView, carManagementView);
        mainFrameSetter.accept(mainFrame);
        return mainFrame;
    }

    private CarOfferView createCarOfferView(MessageDialog messageDialog) {
        DataSource rentalDataSource = h2DataSourceConfig.getCarRentalDataSource();
        JdbcTemplate rentalJdbcTemplate = h2DataSourceConfig.getCarRentalJdbcTemplate(n -> rentalDataSource);

        CarBookingUseCaseMain carBookingUseCaseMain = new CarBookingUseCaseMain();
        CarBookingUseCase carBookingUseCase = carBookingUseCaseMain.createCarBookingUseCase(rentalJdbcTemplate, domainEventSubscriberList);

        CarOfferUseCaseMain carOfferUseCaseMain = new CarOfferUseCaseMain();
        CarOfferUseCase carOfferUseCase = carOfferUseCaseMain.createCarOfferUseCase(rentalJdbcTemplate);

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);

        CarOfferController carOfferController = carOfferUIConfig.getCarOfferController(carOfferUseCase, messageDialog);
        CarBookingController carBookingController = carOfferUIConfig.getCarBookingController(carBookingUseCase, carOfferController);

        return carOfferUIConfig.getCarOfferView(carOfferController, carBookingController);
    }

    private CarManagementView createCarManagementView(MessageDialog messageDialog) {
        DataSource managementDataSource = h2DataSourceConfig.getManagementDataSource();
        JdbcTemplate managementJdbcTemplate = h2DataSourceConfig.getManagementJdbcTemplate(n -> managementDataSource);

        CreateCarBookingMain carBookingMain = new CreateCarBookingMain();
        DomainEventSubscriber domainEventSubscriber = carBookingMain.createDomainEventSubscriber(managementJdbcTemplate);
        domainEventSubscriberList.add(domainEventSubscriber);

        ListCarBookingMain listCarBookingMain = new ListCarBookingMain();
        ListBookingsUseCase listBookingUseCase = listCarBookingMain.createListBookingUseCase(managementJdbcTemplate);

        PickupCarUseCaseMain pickupCarUseCaseMain = new PickupCarUseCaseMain();
        PickupCarUseCase pickupCarUseCase = pickupCarUseCaseMain.createPickupCarUseCase(managementJdbcTemplate);

        ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
        ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
        PickupUIConfig pickupUIConfig = new PickupUIConfig();
        PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
        ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);

        CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
        return carManagementUIConfig.getCarManagementView(listCarBookingView);
    }

}
