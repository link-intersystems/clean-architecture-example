package com.link_intersystems.carrental.offer;


import com.link_intersystems.jdbc.JdbcTemplate;

public class CarOfferComponent {

    public CarOfferUseCase createCarOfferUseCase(JdbcTemplate jdbcTemplate) {
        CarOfferRepository repository = new H2CarOfferRepository(jdbcTemplate);
        return new CarOfferInteractor(repository);
    }
}
