package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventBus;
import com.link_intersystems.jdbc.JdbcTemplate;

public class CarBookingComponent {

    public CarBookingUseCase getCarBookingUseCase(JdbcTemplate jdbcTemplate, DomainEventBus domainEventBus) {
        CarBookingRepository repository = new H2CarBookingRepository(jdbcTemplate);
        return new CarBookingInteractor(repository, domainEventBus);
    }
}
