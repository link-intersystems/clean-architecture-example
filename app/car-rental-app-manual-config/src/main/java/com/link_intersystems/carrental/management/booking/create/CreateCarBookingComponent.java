package com.link_intersystems.carrental.management.booking.create;


import com.link_intersystems.jdbc.JdbcTemplate;

public class CreateCarBookingComponent {

    public CreateCarBookingUseCase getCreateCarBookingUseCase(JdbcTemplate managementJdbcTemplate) {
        H2CreateCarBookingRepository repository = new H2CreateCarBookingRepository(managementJdbcTemplate);
        return new CreateCarBookingInteractor(repository);
    }
}
