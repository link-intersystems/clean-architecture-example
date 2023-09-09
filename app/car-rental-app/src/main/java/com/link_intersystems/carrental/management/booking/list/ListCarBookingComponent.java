package com.link_intersystems.carrental.management.booking.list;


import com.link_intersystems.jdbc.JdbcTemplate;

import javax.sql.DataSource;

public class ListCarBookingComponent {

    private final DataSource dataSource;

    public ListCarBookingComponent(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public ListBookingsUseCase getListBookingsUseCase() {
        JdbcTemplate managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        H2ListBookingsRepository repository = new H2ListBookingsRepository(managementJdbcTemplate);
        return new ListBookingsInteractor(repository);

    }
}
