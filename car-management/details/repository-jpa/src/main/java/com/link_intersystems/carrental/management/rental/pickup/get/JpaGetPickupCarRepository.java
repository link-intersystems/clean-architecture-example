package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.rental.CarRental;
import com.link_intersystems.carrental.management.rental.JpaCarRental;
import jakarta.persistence.EntityManager;

class JpaGetPickupCarRepository implements GetPickupCarRepository {

    private final EntityManager entityManager;

    public JpaGetPickupCarRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CarRental find(BookingNumber bookingNumber) {
        JpaCarRental jpaCarRental = entityManager.find(JpaCarRental.class, bookingNumber.getValue());
        if (jpaCarRental != null) {
            return jpaCarRental.getDomainObject();
        }
        return null;
    }

}
