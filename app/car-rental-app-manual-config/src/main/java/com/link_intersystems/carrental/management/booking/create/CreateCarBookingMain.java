package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.DomainEventSubscriber;
import org.springframework.jdbc.core.JdbcTemplate;

public class CreateCarBookingMain {

    private CreateCarBookingUseCaseConfig useCaseConfig = new CreateCarBookingUseCaseConfig();
    private CreateCarBookingEventSubscriberConfig eventSubscriberConfig = new CreateCarBookingEventSubscriberConfig();
    private H2CreateCarBookingRepositoryConfig repositoryConfig = new H2CreateCarBookingRepositoryConfig();


    public DomainEventSubscriber createDomainEventSubscriber(JdbcTemplate jdbcTemplate) {
        CreateCarBookingUseCase listBookingUseCase = createListBookingUseCase(jdbcTemplate);
        return eventSubscriberConfig.getCarBookedEventSubscriber(listBookingUseCase);
    }

    private CreateCarBookingUseCase createListBookingUseCase(JdbcTemplate jdbcTemplate) {
        CreateCarBookingRepository repository = repositoryConfig.getCreateCarBookingRepository(jdbcTemplate);
        return new CreateCarBookingInteractor(repository);
    }
}
