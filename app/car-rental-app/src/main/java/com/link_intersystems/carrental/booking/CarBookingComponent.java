package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventBusTemplate;
import com.link_intersystems.carrental.DomainEventSubscriber;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.List;

public class CarBookingComponent {

    public CarBookingUseCase getCarBookingUseCase(JdbcTemplate jdbcTemplate, List<DomainEventSubscriber> subscribers) {
        CarBookingRepository repository = new H2CarBookingRepository(jdbcTemplate);
        CarBookingInteractor carBookingInteractor = new CarBookingInteractor(repository);
        DomainEventBusTemplate domainEventBusTemplate = new DomainEventBusTemplate(subscribers);
        return request -> domainEventBusTemplate.execute(() -> carBookingInteractor.bookCar(request));
    }
}
