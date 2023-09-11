package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaCarBookingComponent extends CarBookingComponent {

    private EntityManager entityManager;

    public JpaCarBookingComponent(AOPConfig aopConfig, EntityManager entityManager) {
        super(aopConfig);
        this.entityManager = entityManager;
    }

    @Override
    protected CarBookingRepository getRepository() {
        return new JpaCarBookingRepository(entityManager);
    }
}
