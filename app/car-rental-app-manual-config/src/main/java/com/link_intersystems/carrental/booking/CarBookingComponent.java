package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.List;

public class CarBookingComponent {

    public CarBookingUseCase getCarBookingUseCase(JdbcTemplate jdbcTemplate, List<DomainEventSubscriber> subsribers) {
        CarBookingRepository repository = new H2CarBookingRepository(jdbcTemplate);
        return new CarBookingInteractor(repository);
    }
}
