package com.link_intersystems.carrental.management.booking.list;

import org.springframework.jdbc.core.JdbcTemplate;

public class ListCarBookingMain {

    private H2ListBookingsRepositoryConfig repositoryConfig = new H2ListBookingsRepositoryConfig();

    public ListBookingsUseCase createListBookingUseCase(JdbcTemplate jdbcTemplate) {
        ListBookingsRepository repository = repositoryConfig.getListBookingsRepository(name -> jdbcTemplate);
        ListCarBookingUseCaseConfig listCarBookingUseCaseConfig = new ListCarBookingUseCaseConfig();
        return listCarBookingUseCaseConfig.getListCarBookingsUseCase(repository);
    }
}
