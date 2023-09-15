package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaReturnCarComponent extends ReturnCarComponent {

    private final EntityManager managementEntityManager;

    public JpaReturnCarComponent(AOPConfig aopConfig, EntityManager managementEntityManager) {
        super(aopConfig);
        this.managementEntityManager = managementEntityManager;
    }

    @Override
    protected ReturnCarRepository getRepository() {
        return new JpaReturnCarRepository(managementEntityManager);
    }
}
