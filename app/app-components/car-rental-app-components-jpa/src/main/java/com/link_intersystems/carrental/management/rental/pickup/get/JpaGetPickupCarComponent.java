package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaGetPickupCarComponent extends GetPickupCarComponent {


    private EntityManager managementEntityManager;

    public JpaGetPickupCarComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.managementEntityManager = managementEntityManager;
    }

    @Override
    protected GetPickupCarRepository getRepository() {
        return new JpaGetPickupCarRepository(managementEntityManager);
    }
}
