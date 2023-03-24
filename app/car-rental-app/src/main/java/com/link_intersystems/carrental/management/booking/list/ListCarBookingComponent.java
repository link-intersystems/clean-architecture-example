package com.link_intersystems.carrental.management.booking.list;


import com.link_intersystems.jdbc.JdbcTemplate;

public class ListCarBookingComponent {

    public ListBookingsUseCase getListBookingsUseCase(JdbcTemplate managementJdbcTemplate) {
        H2ListBookingsRepository repository = new H2ListBookingsRepository(managementJdbcTemplate);
        return new ListBookingsInteractor(repository);

    }
}
