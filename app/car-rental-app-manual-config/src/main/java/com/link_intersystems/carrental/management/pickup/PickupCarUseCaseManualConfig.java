package com.link_intersystems.carrental.management.pickup;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PickupCarUseCaseManualConfig {

    public PickupCarUseCase getPickupCarUseCase(DataSource dataSource) {
        H2PickupCarRepositoryConfig repositoryConfig = new H2PickupCarRepositoryConfig();

        PickupCarRepository repository = repositoryConfig.getPickupCarRepository(n -> new JdbcTemplate(dataSource));
        return new PickupCarInteractor(repository);
    }
}
