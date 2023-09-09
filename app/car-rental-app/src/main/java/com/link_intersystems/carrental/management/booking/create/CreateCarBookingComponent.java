package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.jdbc.JdbcTemplate;

import javax.sql.DataSource;

public class CreateCarBookingComponent {

    private final DataSource dataSource;

    public CreateCarBookingComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CreateCarBookingUseCase getCreateCarBookingUseCase() {
        JdbcTemplate managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        H2CreateCarBookingRepository repository = new H2CreateCarBookingRepository(managementJdbcTemplate);
        return new CreateCarBookingInteractor(repository);
    }
}
