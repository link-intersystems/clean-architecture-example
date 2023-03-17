package com.link_intersystems.carrental.management.rental.pickup.get;

import org.springframework.jdbc.core.JdbcTemplate;

public class GetPickupCarComponent {

    public GetPickupCarUseCase getGetPickupCarUseCase(JdbcTemplate managementJdbcTemplate) {
        GetPickupCarRepository getPickupCarRepository = new H2GetPickupCarRepository(managementJdbcTemplate);
        return new GetPickupCarInteractor(getPickupCarRepository);
    }
}
