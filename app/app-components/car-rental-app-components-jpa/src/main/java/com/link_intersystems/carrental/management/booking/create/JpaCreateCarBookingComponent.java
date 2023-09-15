package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaCreateCarBookingComponent extends CreateCarBookingComponent {


    private final EntityManager entityManager;

    public JpaCreateCarBookingComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.entityManager = managementEntityManager;
    }

    @Override
    protected CreateCarBookingRepository getRepository() {
        return new JpaCreateCarBookingRepository(entityManager);
    }
}
