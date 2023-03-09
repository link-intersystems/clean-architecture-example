package com.link_intersystems.carrental.management.booking.list;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ListCarBookingManualConfig {

    private ListCarBookingUseCaseConfig useCaseConfig = new ListCarBookingUseCaseConfig();
    private H2ListBookingRepositoryConfig repositoryConfig = new H2ListBookingRepositoryConfig();

    public ListBookingsUseCase getListBookingUseCase(DataSource managementDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(managementDataSource);
        ListBookingsRepository repository = repositoryConfig.getListBookingsRepository(name -> jdbcTemplate);
        return new ListBookingsInteractor(repository);
    }
}
