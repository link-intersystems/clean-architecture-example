package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.MessageDialog;
import com.link_intersystems.carrental.booking.*;
import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.BiConsumer;

public class CarOfferServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        CarSearchModel carSearchModel = new CarSearchModel();

        carSearchModel.getVehicleType().setValue("MICRO");
        carSearchModel.getPickupDate().setValue(LocalDateTime.now().plusDays(1).toString());
        carSearchModel.getReturnDate().setValue(LocalDateTime.now().plusDays(2).toString());

        registerService.accept(CarSearchModel.class, carSearchModel);

        CarOfferRepository carOfferRepository = applicationContext.getService(CarOfferRepository.class);
        CarOfferUseCase carOfferUseCase = new CarOfferInteractor(carOfferRepository);

        MessageDialog messageDialog = applicationContext.getService(MessageDialog.class);
        CarOfferController carOfferController = new CarOfferController(carOfferUseCase, carSearchModel);
        carOfferController.setMessageDialog(messageDialog);
        registerService.accept(CarOfferController.class, carOfferController);

        CarBookingUseCase carBookingUseCase = applicationContext.getService(CarBookingUseCase.class);

        CarBookingController carBookingController = new CarBookingController(carBookingUseCase, carSearchModel, messageDialog);
        carBookingController.setOnDoneActionListener(carOfferController);
        registerService.accept(CarBookingController.class, carBookingController);


        CarOfferView carOfferView = new CarOfferView();
        carOfferView.setCarOfferListModel(carOfferController.getCarOfferListModel());
        carOfferView.setCarSearchModel(carOfferController.getCarSearchModel());
        carOfferView.setCarSearchAction(carOfferController);
        carOfferView.setBookCarAction(carBookingController);
        carOfferView.getSelectionProvider().addSelectionChangedListener(carBookingController.getSelectionListener());
        registerService.accept(CarOfferView.class, carOfferView);

    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(CarOfferView.class);
    }
}
