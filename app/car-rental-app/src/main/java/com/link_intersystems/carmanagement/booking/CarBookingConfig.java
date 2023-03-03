package com.link_intersystems.carmanagement.booking;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.carrental.management.H2ListBookingsRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public class CarBookingConfig {

    public ListBookingsRepository getListBookingsRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2ListBookingsRepository(beanSelector.select("getManagementJdbcTemplate"));
    }

    public ListBookingsUseCase getListCarBookingsUseCase(ListBookingsRepository listBookingsRepository) {
        return new ListBookingsInteractor(listBookingsRepository);
    }
}
