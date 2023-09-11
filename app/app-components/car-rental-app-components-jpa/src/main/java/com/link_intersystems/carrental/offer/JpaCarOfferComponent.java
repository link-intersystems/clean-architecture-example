package com.link_intersystems.carrental.offer;

import com.link_intersystems.carrental.components.AOPConfig;
import jakarta.persistence.EntityManager;

public class JpaCarOfferComponent extends CarOfferComponent {

    private EntityManager entityManager;

    public JpaCarOfferComponent(AOPConfig aopConfig, EntityManager entityManager) {
        super(aopConfig);
        this.entityManager = entityManager;
    }

    @Override
    protected CarOfferRepository getRepository() {
        return new JpaCarOfferRepository(entityManager);
    }
}
