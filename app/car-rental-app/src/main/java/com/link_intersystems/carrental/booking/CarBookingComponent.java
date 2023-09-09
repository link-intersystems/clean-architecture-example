package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.jdbc.JdbcTemplate;
import jakarta.persistence.EntityManager;

public class CarBookingComponent {


    private EntityManager entityManager;

    public CarBookingComponent(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CarBookingUseCase getCarBookingUseCase(DomainEventPublisher eventPublisher) {
        CarBookingRepository repository = new JpaCarBookingRepository(entityManager);
        return new CarBookingInteractor(repository, eventPublisher);
    }
}
