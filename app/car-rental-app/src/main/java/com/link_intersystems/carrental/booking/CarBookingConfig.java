package com.link_intersystems.carrental.booking;

import com.link_intersystems.app.context.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class CarBookingConfig {

    public CarBookingRepository getCarBookingRepository(BeanSelector<JdbcTemplate> beanSelector) {
        return new H2CarBookingRepository(beanSelector.select("getCarRentalJdbcTemplate"));
    }

    public CarBookingUseCase getCarBookingUseCase(CarBookingRepository carBookingRepository) {
        return new CarBookingInteractor(carBookingRepository);
    }

}
