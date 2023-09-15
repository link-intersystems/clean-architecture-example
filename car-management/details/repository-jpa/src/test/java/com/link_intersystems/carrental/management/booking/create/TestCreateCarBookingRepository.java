package com.link_intersystems.carrental.management.booking.create;

import jakarta.persistence.EntityManager;

public class TestCreateCarBookingRepository extends JpaCreateCarBookingRepository {

    public TestCreateCarBookingRepository(EntityManager entityManager) {
        super(entityManager);
    }
}
