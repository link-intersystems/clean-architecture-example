package com.link_intersystems.carrental.offer;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CarOfferUseCaseManualConfig {

    public CarOfferUseCase getCarOfferUseCase(DataSource dataSource) {
        H2CarOfferRepositoryConfig repositoryConfig = new H2CarOfferRepositoryConfig();
        CarOfferRepository repository = repositoryConfig.getCarOfferRepository(n -> new JdbcTemplate(dataSource));
        return new CarOfferInteractor(repository);
    }
}
