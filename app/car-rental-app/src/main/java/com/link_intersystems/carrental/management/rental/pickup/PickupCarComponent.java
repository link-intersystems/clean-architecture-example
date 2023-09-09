package com.link_intersystems.carrental.management.rental.pickup;

import com.link_intersystems.jdbc.JdbcTemplate;

import javax.sql.DataSource;

public class PickupCarComponent {

    private final DataSource dataSource;

    public PickupCarComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PickupCarUseCase getPickupCarUseCase() {
        JdbcTemplate managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        PickupCarRepository repository = new H2PickupCarRepository(managementJdbcTemplate);
        return new PickupCarInteractor(repository);
    }
}
