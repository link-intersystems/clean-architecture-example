package com.link_intersystems.carrental.booking;

import com.link_intersystems.jpa.JpaConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;

public class CarBookingJpaConfig {

    private final EntityManagerFactory emf;

    public CarBookingJpaConfig(DataSource dataSource) {
        JpaConfig jpaConfig = new JpaConfig(dataSource);
        emf = jpaConfig.getEntityManagerFactory("BOOKING", JpaCar.class, JpaCarBooking.class, JpaRentalCar.class, JpaCustomer.class);
    }

    public EntityManager newEntityManager() {
        return emf.createEntityManager();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
