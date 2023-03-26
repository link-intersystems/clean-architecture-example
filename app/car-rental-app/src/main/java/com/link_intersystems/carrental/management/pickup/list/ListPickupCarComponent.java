package com.link_intersystems.carrental.management.pickup.list;


import com.link_intersystems.jdbc.JdbcTemplate;

public class ListPickupCarComponent {

    public ListPickupCarUseCase getListPickupCarUseCase(JdbcTemplate managementJdbcTemplate) {
        ListPickupCarRepository repository = new H2ListPickupCarRepository(managementJdbcTemplate);
        return new ListPickupCarInteractor(repository);
    }
}
