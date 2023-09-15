package com.link_intersystems.carrental.management.booking.list;


import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaListCarBookingComponent extends ListCarBookingComponent {

    private final EntityManager managementEntityManager;

    public JpaListCarBookingComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.managementEntityManager = managementEntityManager;
    }

    @Override
    protected ListBookingsRepository getRepository() {
        return new JpaListBookingsRepository(managementEntityManager);
    }
}
