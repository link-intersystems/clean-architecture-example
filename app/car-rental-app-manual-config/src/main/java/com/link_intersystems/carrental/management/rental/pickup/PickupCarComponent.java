package com.link_intersystems.carrental.management.rental.pickup;

import org.springframework.jdbc.core.JdbcTemplate;

public class PickupCarComponent {

    public PickupCarUseCase getPickupCarUseCase(JdbcTemplate managementJdbcTemplate) {
        PickupCarRepository repository = new H2PickupCarRepository(managementJdbcTemplate);
        return new PickupCarInteractor(repository);
    }
}
