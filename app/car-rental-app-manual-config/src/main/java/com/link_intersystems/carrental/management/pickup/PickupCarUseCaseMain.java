package com.link_intersystems.carrental.management.pickup;

import com.link_intersystems.carrental.management.rental.pickup.H2PickupCarRepositoryConfig;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarRepository;
import com.link_intersystems.carrental.management.rental.pickup.PickupCarUseCase;
import com.link_intersystems.carrental.management.rental.pickup.PickupUseCaseConfig;
import org.springframework.jdbc.core.JdbcTemplate;

public class PickupCarUseCaseMain {

    public PickupCarUseCase createPickupCarUseCase(JdbcTemplate jdbcTemplate) {
        H2PickupCarRepositoryConfig repositoryConfig = new H2PickupCarRepositoryConfig();
        PickupCarRepository repository = repositoryConfig.getPickupCarRepository(n -> jdbcTemplate);
        PickupUseCaseConfig pickupUseCaseConfig = new PickupUseCaseConfig();
        return pickupUseCaseConfig.getPickupCarUseCase(repository);
    }
}
