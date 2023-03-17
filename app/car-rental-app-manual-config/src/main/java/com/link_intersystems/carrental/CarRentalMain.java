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
import com.link_intersystems.carrental.management.rental.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.pickup.PickupCarUseCaseMain;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarRepository;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.get.GetPickupUseCaseConfig;
import com.link_intersystems.carrental.management.rental.pickup.get.H2GetPickupCarRepositoryConfig;
import com.link_intersystems.carrental.management.rental.pickup.list.H2ListPickupCarRepositoryConfig;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarRepository;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.list.ListPickupUseCaseConfig;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarController;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarUIConfig;
import com.link_intersystems.carrental.management.pickup.list.ui.ListPickupCarView;
import com.link_intersystems.carrental.management.pickup.ui.PickupCarController;
import com.link_intersystems.carrental.management.pickup.ui.PickupUIConfig;
import com.link_intersystems.carrental.management.rental.returnCar.H2ReturnCarRepositoryConfig;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarRepository;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnCarUseCase;
import com.link_intersystems.carrental.management.rental.returnCar.ReturnUseCaseConfig;
import com.link_intersystems.carrental.management.returnCar.ui.ReturnCarFormController;
import com.link_intersystems.carrental.management.returnCar.ui.ReturnCarUIConfig;
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

        ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
        H2ListPickupCarRepositoryConfig h2ListPickupCarRepositoryConfig = new H2ListPickupCarRepositoryConfig();
        ListPickupCarRepository listPickupCarRepository = h2ListPickupCarRepositoryConfig.getListPickupCarRepository(managementJdbcTemplate);
        ListPickupUseCaseConfig listPickupUseCaseConfig = new ListPickupUseCaseConfig();
        ListPickupCarUseCase listPickupCarUseCase = listPickupUseCaseConfig.getListPickupCarUseCase(listPickupCarRepository);
        ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(messageDialog, listPickupCarUseCase);

        ReturnUseCaseConfig returnUseCaseConfig = new ReturnUseCaseConfig();
        H2ReturnCarRepositoryConfig h2ReturnCarRepositoryConfig = new H2ReturnCarRepositoryConfig();
        ReturnCarRepository returnCarUseCaseRepository = h2ReturnCarRepositoryConfig.getReturnCarRepository(managementJdbcTemplate);
        ReturnCarUseCase returnCarUseCase = returnUseCaseConfig.getReturnCarUseCase(returnCarUseCaseRepository);
        ReturnCarUIConfig returnCarUIConfig = new ReturnCarUIConfig();
        GetPickupUseCaseConfig getPickupUseCaseConfig = new GetPickupUseCaseConfig();

        H2GetPickupCarRepositoryConfig h2GetPickupCarRepositoryConfig = new H2GetPickupCarRepositoryConfig();
        GetPickupCarRepository getPickupCarRepository = h2GetPickupCarRepositoryConfig.getGetPickupCarRepository(managementJdbcTemplate);
        GetPickupCarUseCase getPickupCarUseCase = getPickupUseCaseConfig.getGetPickupCarUseCase(getPickupCarRepository);
        ReturnCarFormController returnCarFormController = returnCarUIConfig.getReturnCarController(getPickupCarUseCase, messageDialog, listPickupCarController, returnCarUseCase);

        ListPickupCarView listPickupCarView = listPickupCarUIConfig.getListPickupCarView(listPickupCarController, returnCarFormController);
        CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
        return carManagementUIConfig.getCarManagementView(listCarBookingView, listPickupCarView);
    }

}
