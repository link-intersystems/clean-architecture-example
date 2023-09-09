package com.link_intersystems.carrental.management.rental.pickup.get;

import com.link_intersystems.jdbc.JdbcTemplate;

import javax.sql.DataSource;

public class GetPickupCarComponent {

    private final DataSource dataSource;

    public GetPickupCarComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public GetPickupCarUseCase getGetPickupCarUseCase() {
        JdbcTemplate managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        GetPickupCarRepository getPickupCarRepository = new H2GetPickupCarRepository(managementJdbcTemplate);
        return new GetPickupCarInteractor(getPickupCarRepository);
    }
}
