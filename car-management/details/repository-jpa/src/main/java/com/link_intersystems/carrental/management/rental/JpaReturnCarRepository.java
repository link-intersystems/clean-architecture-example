package com.link_intersystems.carrental.management.rental;

import com.link_intersystems.carrental.booking.BookingNumber;
import jakarta.persistence.EntityManager;

class JpaReturnCarRepository implements ReturnCarRepository {

    private final EntityManager entityManager;

    public JpaReturnCarRepository(EntityManager entityManager) {
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

    @Override
    public void update(CarRental carRental) {
        BookingNumber bookingNumber = carRental.getBookingNumber();
        JpaCarRental jpaCarRental = entityManager.find(JpaCarRental.class, bookingNumber.getValue());
        if (jpaCarRental == null) {
            throw new IllegalStateException("Unable to persist CarReturn " + bookingNumber);
        }
        jpaCarRental.update(carRental);
        entityManager.merge(jpaCarRental);
    }

}
