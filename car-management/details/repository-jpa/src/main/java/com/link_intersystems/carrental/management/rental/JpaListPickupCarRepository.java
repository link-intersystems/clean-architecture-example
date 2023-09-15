package com.link_intersystems.carrental.management.rental;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

class JpaListPickupCarRepository implements ListPickupCarRepository {

    private final EntityManager entityManager;

    public JpaListPickupCarRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CarRental> findAll() {
        TypedQuery<JpaCarRental> findAllQuery = entityManager.createQuery("FROM CarRental cr WHERE cr.returnTime is null", JpaCarRental.class);
        return findAllQuery.getResultList().stream().map(JpaCarRental::getDomainObject).toList();
    }

}
