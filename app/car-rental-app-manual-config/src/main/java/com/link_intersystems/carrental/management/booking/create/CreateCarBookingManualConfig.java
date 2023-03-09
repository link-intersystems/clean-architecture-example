package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.DomainEventSubscriber;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CreateCarBookingManualConfig {

    private CreateCarBookingUseCaseConfig useCaseConfig = new CreateCarBookingUseCaseConfig();
    private CreateCarBookingEventSubscriberConfig eventSubscriberConfig = new CreateCarBookingEventSubscriberConfig();
    private H2CreateCarBookingRepositoryConfig repositoryConfig = new H2CreateCarBookingRepositoryConfig();


    public DomainEventSubscriber getDomainEventSubscriber(DataSource managementDataSource) {
        CreateCarBookingUseCase listBookingUseCase = getListBookingUseCase(managementDataSource);
        return eventSubscriberConfig.getCarBookedEventSubscriber(listBookingUseCase);
    }

    private CreateCarBookingUseCase getListBookingUseCase(DataSource managementDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(managementDataSource);
        CreateCarBookingRepository repository = repositoryConfig.getCreateCarBookingRepository(name -> jdbcTemplate);
        return new CreateCarBookingInteractor(repository);
    }
}
