package com.link_intersystems.carrental.management.booking.create;

import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.JpaCarBooking;
import jakarta.persistence.EntityManager;

class JpaCreateCarBookingRepository implements CreateCarBookingRepository {

    private final EntityManager entityManager;

    public JpaCreateCarBookingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void persist(CarBooking carBooking) {
        JpaCarBooking jpaCarBooking = new JpaCarBooking();
        jpaCarBooking.update(carBooking);
        entityManager.merge(jpaCarBooking);
    }
}
