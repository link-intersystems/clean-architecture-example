package com.link_intersystems.carrental.offer;

import org.springframework.jdbc.core.JdbcTemplate;

public class CarOfferComponent {

    public CarOfferUseCase createCarOfferUseCase(JdbcTemplate jdbcTemplate) {
        CarOfferRepository repository = new H2CarOfferRepository(jdbcTemplate);
        return new CarOfferInteractor(repository);
    }
}
