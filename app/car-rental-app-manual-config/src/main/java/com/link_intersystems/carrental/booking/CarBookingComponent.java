package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventSubscriber;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CarBookingComponent {

    public CarBookingUseCase getCarBookingUseCase(JdbcTemplate jdbcTemplate, List<DomainEventSubscriber> subsribers) {
        CarBookingRepository repository = new H2CarBookingRepository(jdbcTemplate);
        return new CarBookingInteractor(repository);
    }
}
