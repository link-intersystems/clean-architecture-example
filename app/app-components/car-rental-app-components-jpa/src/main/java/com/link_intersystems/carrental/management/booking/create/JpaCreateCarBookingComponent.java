package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import com.link_intersystems.carrental.management.booking.JpaUpdateCarBookingRentalRepository;
import jakarta.persistence.EntityManager;

public class JpaCreateCarBookingComponent extends CreateCarBookingComponent {


    private final EntityManager entityManager;

    public JpaCreateCarBookingComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.entityManager = managementEntityManager;
    }

    @Override
    protected CreateCarBookingRepository getCreateCarBookingRepository() {
        return new JpaCreateCarBookingRepository(entityManager);
    }

    @Override
    protected UpdateCarBookingRentalRepository getUpdateCarBookingRentalRepository() {
        return new JpaUpdateCarBookingRentalRepository(entityManager);
    }
}
