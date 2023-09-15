package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.booking.CarBooking;
import com.link_intersystems.carrental.management.booking.JpaCarBooking;
import jakarta.persistence.EntityManager;

class JpaPickupCarRepository implements PickupCarRepository {

    private EntityManager entityManager;

    public JpaPickupCarRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persist(CarRental carRental) {
        JpaCarRental jpaCarRental = new JpaCarRental();
        jpaCarRental.update(carRental);
        entityManager.merge(jpaCarRental);
    }

    @Override
    public CarBooking findBooking(BookingNumber bookingNumber) {
        JpaCarBooking jpaCarBooking = entityManager.find(JpaCarBooking.class, bookingNumber.getValue());
        if (jpaCarBooking != null) {
            return jpaCarBooking.getDomainObject();
        }

        return null;
    }

}
