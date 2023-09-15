package com.link_intersystems.carrental.management.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

class JpaListBookingsRepository implements ListBookingsRepository {

    private final EntityManager entityManager;

    public JpaListBookingsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CarBooking> findBookings() {
        TypedQuery<JpaCarBooking> findBookingsQuery = entityManager.createQuery("FROM CarBooking cb WHERE cb.rentalState is null", JpaCarBooking.class);

        List<JpaCarBooking> resultList = findBookingsQuery.getResultList();

        return resultList.stream().map(JpaCarBooking::getDomainObject).collect(Collectors.toList());
    }


}
