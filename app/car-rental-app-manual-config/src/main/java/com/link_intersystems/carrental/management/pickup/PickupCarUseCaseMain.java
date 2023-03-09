package com.link_intersystems.carrental.management.pickup;

import org.springframework.jdbc.core.JdbcTemplate;

public class PickupCarUseCaseMain {

    public PickupCarUseCase createPickupCarUseCase(JdbcTemplate jdbcTemplate) {
        H2PickupCarRepositoryConfig repositoryConfig = new H2PickupCarRepositoryConfig();
        PickupCarRepository repository = repositoryConfig.getPickupCarRepository(n -> jdbcTemplate);
        PickupUseCaseConfig pickupUseCaseConfig = new PickupUseCaseConfig();
        return pickupUseCaseConfig.getPickupCarUseCase(repository);
    }
}
