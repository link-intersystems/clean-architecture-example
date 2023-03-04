package com.link_intersystems.carmanagement.booking.list;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.carrental.management.bookings.ListCarBookingController;
import com.link_intersystems.carrental.management.bookings.ListCarBookingView;
import org.springframework.jdbc.core.JdbcTemplate;

public class CarBookingConfig {

    public ListBookingsRepository getListBookingsRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2ListBookingsRepository(beanSelector.select("getManagementJdbcTemplate"));
    }

    public ListBookingsUseCase getListCarBookingsUseCase(ListBookingsRepository listBookingsRepository) {
        return new ListBookingsInteractor(listBookingsRepository);
    }

    public ListCarBookingController getListCarBookingController(ListBookingsUseCase listBookingsUseCase) {
        return new ListCarBookingController(listBookingsUseCase);
    }

    public ListCarBookingView getListCarBookingView(ListCarBookingController listCarBookingController) {
        ListCarBookingView listCarBookingView = new ListCarBookingView();
        listCarBookingView.setListCarBookingsAction(listCarBookingController);
        listCarBookingView.setListCarBookingModel(listCarBookingController.getCarBookingListModel());
        return listCarBookingView;
    }
}
