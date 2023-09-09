package com.link_intersystems.carrental.management.rental.pickup.list;


import com.link_intersystems.jdbc.JdbcTemplate;

import javax.sql.DataSource;

public class ListPickupCarComponent {

    private final DataSource dataSource;

    public ListPickupCarComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ListPickupCarUseCase getListPickupCarUseCase() {
        JdbcTemplate managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        ListPickupCarRepository repository = new H2ListPickupCarRepository(managementJdbcTemplate);
        return new ListPickupCarInteractor(repository);
    }
}
