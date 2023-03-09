package com.link_intersystems.carrental.booking;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CarBookingUseCaseManualConfig {


    public CarBookingUseCase getCarBookingUseCase(DataSource dataSource) {
        H2CarBookingRepositoryConfig repositoryConfig = new H2CarBookingRepositoryConfig();
        CarBookingRepository repository = repositoryConfig.getCarBookingRepository(n -> new JdbcTemplate(dataSource));
        return new CarBookingInteractor(repository);
    }
}
