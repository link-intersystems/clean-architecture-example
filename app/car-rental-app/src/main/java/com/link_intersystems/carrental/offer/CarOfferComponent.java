package com.link_intersystems.carrental.offer;


import jakarta.persistence.EntityManager;

public class CarOfferComponent {

    private EntityManager entityManager;

    public CarOfferComponent(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CarOfferUseCase createCarOfferUseCase() {
        CarOfferRepository repository = new JpaCarOfferRepository(entityManager);
        return new CarOfferInteractor(repository);
    }
}
