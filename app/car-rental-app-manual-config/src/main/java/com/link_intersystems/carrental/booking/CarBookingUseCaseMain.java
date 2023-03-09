package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventSubscriber;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CarBookingUseCaseMain {

    public CarBookingUseCase createCarBookingUseCase(JdbcTemplate jdbcTemplate, List<DomainEventSubscriber> subsribers) {
        H2CarBookingRepositoryConfig repositoryConfig = new H2CarBookingRepositoryConfig();
        CarBookingRepository repository = repositoryConfig.getCarBookingRepository(n -> jdbcTemplate);
        CarBookingUseCaseConfig carBookingUseCaseConfig = new CarBookingUseCaseConfig();
        return carBookingUseCaseConfig.getCarBookingUseCase(repository, subsribers);
    }
}
