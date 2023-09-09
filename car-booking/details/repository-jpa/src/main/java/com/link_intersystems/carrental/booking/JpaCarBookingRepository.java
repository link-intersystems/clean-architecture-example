package com.link_intersystems.carrental.booking;

import com.link_intersystems.carrental.CarId;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.carrental.time.Period;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

class JpaCarBookingRepository implements CarBookingRepository {

    private final EntityManager entityManager;

    public JpaCarBookingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CarBooking findBooking(CarId carId, Period bookingPeriod) {
        TypedQuery<JpaCarBooking> query = entityManager.createQuery("""
                   FROM CarBooking cb where cb.carIdValue =:carId and 
                   (
                    (cb.pickupDateTime < :pickup or cb.pickupDateTime <= :pickup) 
                    or 
                    (cb.returnDateTime >= :return or cb.returnDateTime <= :return)
                   )
                   """, JpaCarBooking.class);
        query.setParameter("carId", carId.getValue());
        query.setParameter("pickup", bookingPeriod.getBegin());
        query.setParameter("return", bookingPeriod.getEnd());

        List<JpaCarBooking> jpaCarBookings = query.getResultList();
        List<CarBooking> carBookings = jpaCarBookings.stream().map(JpaCarBooking::getDomainObject).collect(Collectors.toList());

        List<CarBooking> overlappingCarBookings = carBookings.stream().filter(cb -> bookingPeriod.overlaps(cb.getBookingPeriod())).collect(Collectors.toList());
        return overlappingCarBookings.isEmpty() ? null : overlappingCarBookings.get(0);
    }

    @Override
    public void persist(CarBooking carBooking) {
        JpaCarBooking jpaCarBooking = new JpaCarBooking(carBooking);
        entityManager.persist(jpaCarBooking);
        entityManager.flush();
        jpaCarBooking.updateDomainObject();
    }

    @Override
    public Customer findCustomer(CustomerId customerId) {
        if (customerId == null) {
            return null;
        }

        JpaCustomer jpaCustomer = entityManager.find(JpaCustomer.class, customerId.getValue());

        if (jpaCustomer == null) {
            return null;
        }

        return jpaCustomer.getDomainObject();
    }

}
