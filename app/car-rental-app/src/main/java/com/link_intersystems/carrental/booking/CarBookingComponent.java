package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.jdbc.JdbcTemplate;

public class CarBookingComponent {

    public CarBookingUseCase getCarBookingUseCase(JdbcTemplate jdbcTemplate, DomainEventPublisher eventPublisher) {
        CarBookingRepository repository = new H2CarBookingRepository(jdbcTemplate);
        return new CarBookingInteractor(repository, eventPublisher);
    }
}
