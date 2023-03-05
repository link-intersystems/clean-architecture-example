package com.link_intersystems.carrental.management.booking.list;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingController;
import com.link_intersystems.carrental.management.booking.list.ui.ListCarBookingView;
import org.springframework.jdbc.core.JdbcTemplate;

public class ListCarBookingConfig {

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
