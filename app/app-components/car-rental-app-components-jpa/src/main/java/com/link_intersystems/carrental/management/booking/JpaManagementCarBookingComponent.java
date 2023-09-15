package com.link_intersystems.carrental.management.booking;


import com.link_intersystems.carrental.components.AOPConfig;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import jakarta.persistence.EntityManager;

public class JpaManagementCarBookingComponent extends ManagementCarBookingComponent {


    private final EntityManager entityManager;

    public JpaManagementCarBookingComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
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

    @Override
    protected ListBookingsRepository getListBookingsRepository() {
        return new JpaListBookingsRepository(entityManager);
    }
}
