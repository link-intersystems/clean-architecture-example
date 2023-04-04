package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.jdbc.JdbcTemplate;

public class GetPickupCarComponent {

    public GetPickupCarUseCase getGetPickupCarUseCase(JdbcTemplate managementJdbcTemplate) {
        GetPickupCarRepository getPickupCarRepository = new H2GetPickupCarRepository(managementJdbcTemplate);
        return new GetPickupCarInteractor(getPickupCarRepository);
    }
}
