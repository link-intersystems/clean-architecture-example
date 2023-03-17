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

        CarBookingComponent carBookingComponent = new CarBookingComponent();
        CarBookingUseCase carBookingUseCase = carBookingComponent.getCarBookingUseCase(rentalJdbcTemplate, domainEventSubscriberList);

        CarOfferComponent carOfferComponent = new CarOfferComponent();
        CarOfferUseCase carOfferUseCase = carOfferComponent.createCarOfferUseCase(rentalJdbcTemplate);

        CarOfferUIConfig carOfferUIConfig = new CarOfferUIConfig(messageDialog);

        CarOfferController carOfferController = carOfferUIConfig.getCarOfferController(carOfferUseCase, messageDialog);
        CarBookingController carBookingController = carOfferUIConfig.getCarBookingController(carBookingUseCase, carOfferController);

        return carOfferUIConfig.getCarOfferView(carOfferController, carBookingController);
    }

    private CarManagementView createCarManagementView(MessageDialog messageDialog) {
        DataSource managementDataSource = h2DataSourceConfig.getManagementDataSource();
        JdbcTemplate managementJdbcTemplate = h2DataSourceConfig.getManagementJdbcTemplate(n -> managementDataSource);

        CreateCarBookingComponent createCarBookingComponent = new CreateCarBookingComponent();
        CreateCarBookingUseCase createCarBookingUseCase = createCarBookingComponent.getCreateCarBookingUseCase(managementJdbcTemplate);
        CarBookedEventSubscriber carBookedEventSubscriber = new CarBookedEventSubscriber(createCarBookingUseCase);
        domainEventSubscriberList.add(carBookedEventSubscriber);

        ListCarBookingComponent listCarBookingComponent = new ListCarBookingComponent();
        ListBookingsUseCase listBookingUseCase = listCarBookingComponent.getListBookingsUseCase(managementJdbcTemplate);

        ListCarBookingUIConfig listCarBookingUIConfig = new ListCarBookingUIConfig();
        ListCarBookingController listCarBookingController = listCarBookingUIConfig.getListCarBookingController(listBookingUseCase);
        PickupUIConfig pickupUIConfig = new PickupUIConfig();

        PickupCarComponent pickupCarComponent = new PickupCarComponent();
        PickupCarUseCase pickupCarUseCase = pickupCarComponent.getPickupCarUseCase(managementJdbcTemplate);

        PickupCarController pickupCarController = pickupUIConfig.getPickupCarController(messageDialog, pickupCarUseCase);
        ListCarBookingView listCarBookingView = listCarBookingUIConfig.getListCarBookingView(listCarBookingController, pickupCarController);


        ListPickupCarComponent listPickupCarComponent = new ListPickupCarComponent();
        ListPickupCarUseCase listPickupCarUseCase = listPickupCarComponent.getListPickupCarUseCase(managementJdbcTemplate);

        ListPickupCarUIConfig listPickupCarUIConfig = new ListPickupCarUIConfig();
        ListPickupCarController listPickupCarController = listPickupCarUIConfig.getPickupCarListController(messageDialog, listPickupCarUseCase);

        ReturnCarComponent returnCarComponent = new ReturnCarComponent();
        ReturnCarUseCase returnCarUseCase = returnCarComponent.getReturnUseCase(managementJdbcTemplate);


        GetPickupCarComponent getPickupCarComponent = new GetPickupCarComponent();
        GetPickupCarUseCase getPickupCarUseCase = getPickupCarComponent.getGetPickupCarUseCase(managementJdbcTemplate);

        ReturnCarUIConfig returnCarUIConfig = new ReturnCarUIConfig();
        ReturnCarFormController returnCarFormController = returnCarUIConfig.getReturnCarController(getPickupCarUseCase, messageDialog, listPickupCarController, returnCarUseCase);

        ListPickupCarView listPickupCarView = listPickupCarUIConfig.getListPickupCarView(listPickupCarController, returnCarFormController);
        CarManagementUIConfig carManagementUIConfig = new CarManagementUIConfig();
        return carManagementUIConfig.getCarManagementView(listCarBookingView, listPickupCarView);
    }

}
