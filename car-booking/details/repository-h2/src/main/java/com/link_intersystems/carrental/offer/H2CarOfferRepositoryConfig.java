package com.link_intersystems.carrental.offer;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2CarOfferRepositoryConfig {

    public CarOfferRepository getCarOfferRepository(BeanSelector<JdbcTemplate> beanSelector) {
        JdbcTemplate carRentalJdbcTemplate = beanSelector.select("getCarRentalJdbcTemplate");
        return new H2CarOfferRepository(carRentalJdbcTemplate);
    }

}
