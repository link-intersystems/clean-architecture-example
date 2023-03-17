package com.link_intersystems.carrental.management.rental.returnCar;

import org.springframework.jdbc.core.JdbcTemplate;

public class H2ReturnCarRepositoryConfig {

    public ReturnCarRepository getReturnCarRepository(JdbcTemplate managementJdbcTemplate) {
        return new H2ReturnCarRepository(managementJdbcTemplate);
    }
}
