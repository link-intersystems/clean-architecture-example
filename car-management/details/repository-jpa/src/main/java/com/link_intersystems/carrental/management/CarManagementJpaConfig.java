package com.link_intersystems.carrental.management;

import com.link_intersystems.carrental.management.booking.JpaCarBooking;
import com.link_intersystems.carrental.management.rental.JpaCarRental;
import com.link_intersystems.jpa.JpaConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;

public class CarManagementJpaConfig {

    private final EntityManagerFactory emf;

    public CarManagementJpaConfig(DataSource dataSource) {
        JpaConfig jpaConfig = new JpaConfig(dataSource);
        emf = jpaConfig.getEntityManagerFactory("MANAGEMENT", JpaCarBooking.class, JpaCarRental.class);
    }

    public EntityManager newEntityManager() {
        return emf.createEntityManager();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
