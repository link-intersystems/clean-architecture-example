package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.carrental.DomainEventSubscriber;
import org.springframework.jdbc.core.JdbcTemplate;

public class CreateCarBookingConfig {

    public DomainEventSubscriber getCarBookedEventSubscriber(CreateCarBookingUseCase createCarBookingUseCase) {
        return new CarBookedEventSubscriber(createCarBookingUseCase);
    }

    public CreateCarBookingUseCase getCreateCarBookingUseCase(CreateCarBookingRepository createCarBookingRepository) {
        return new CreateCarBookingInteractor(createCarBookingRepository);
    }

}
