package com.link_intersystems.carrental.offer;

import org.springframework.jdbc.core.JdbcTemplate;

public class CarOfferUseCaseMain {

    public CarOfferUseCase createCarOfferUseCase(JdbcTemplate jdbcTemplate) {
        H2CarOfferRepositoryConfig repositoryConfig = new H2CarOfferRepositoryConfig();
        CarOfferRepository repository = repositoryConfig.getCarOfferRepository(n -> jdbcTemplate);
        CarOfferUseCaseConfig carOfferUseCaseConfig = new CarOfferUseCaseConfig();
        return carOfferUseCaseConfig.getCarOfferUseCase(repository);
    }
}
