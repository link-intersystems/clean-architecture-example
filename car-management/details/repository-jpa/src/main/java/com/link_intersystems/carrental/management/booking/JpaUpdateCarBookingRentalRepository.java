package com.link_intersystems.carrental.management.booking;

import com.link_intersystems.carrental.booking.BookingNumber;
import com.link_intersystems.carrental.management.UpdateCarBookingRentalRepository;
import jakarta.persistence.EntityManager;

public class JpaUpdateCarBookingRentalRepository implements UpdateCarBookingRentalRepository {

    private final EntityManager entityManager;

    public JpaUpdateCarBookingRentalRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CarBooking findCarBooking(BookingNumber bookingNumber) {
        JpaCarBooking jpaCarBooking = entityManager.find(JpaCarBooking.class, bookingNumber.getValue());
        if (jpaCarBooking != null) {
            return jpaCarBooking.getDomainObject();
        }
        return null;
    }

    @Override
    public void persist(CarBooking carBooking) {
        JpaCarBooking jpaCarBooking = new JpaCarBooking();
        jpaCarBooking.update(carBooking);
        entityManager.merge(jpaCarBooking);
    }
}
