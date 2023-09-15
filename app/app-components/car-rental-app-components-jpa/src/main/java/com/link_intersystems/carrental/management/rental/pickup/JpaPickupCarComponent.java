package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaPickupCarComponent extends PickupCarComponent {

    private final EntityManager managementEntityManager;

    public JpaPickupCarComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.managementEntityManager = managementEntityManager;
    }

    @Override
    protected PickupCarRepository getRepository() {
        return new JpaPickupCarRepository(managementEntityManager);
    }
}
