package com.link_intersystems.carrental.management.rental.pickup.list;


import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaListPickupCarComponent extends ListPickupCarComponent {

    private final EntityManager managementEntityManager;

    public JpaListPickupCarComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.managementEntityManager = managementEntityManager;
    }

    @Override
    protected ListPickupCarRepository getRepository() {
        return new JpaListPickupCarRepository(managementEntityManager);
    }
}
