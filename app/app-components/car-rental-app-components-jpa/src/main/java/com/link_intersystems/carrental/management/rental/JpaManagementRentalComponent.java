package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.DomainEventPublisher;
import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaManagementRentalComponent extends ManagementRentalComponent {


    private EntityManager managementEntityManager;

    public JpaManagementRentalComponent(AOPConfig aopConfig, EntityManager managementEntityManager, DomainEventPublisher eventPublisher) {
        super(aopConfig, eventPublisher);
        this.managementEntityManager = managementEntityManager;
    }

    @Override
    protected GetPickupCarRepository getGetPickupCarRepository() {
        return new JpaGetPickupCarRepository(managementEntityManager);
    }

    @Override
    protected ListPickupCarRepository getListPickupCarRepository() {
        return new JpaListPickupCarRepository(managementEntityManager);
    }

    @Override
    protected PickupCarRepository getPickupCarRepository() {
        return new JpaPickupCarRepository(managementEntityManager);
    }

    @Override
    protected ReturnCarRepository getReturnCarRepository() {
        return new JpaReturnCarRepository(managementEntityManager);
    }
}
