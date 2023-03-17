package com.link_intersystems.carrental.management.rental.returnCar;

import org.springframework.jdbc.core.JdbcTemplate;

public class ReturnCarComponent {

    public ReturnCarUseCase getReturnUseCase(JdbcTemplate managementJdbcTemplate) {
        ReturnCarRepository repository = new H2ReturnCarRepository(managementJdbcTemplate);
        return new ReturnCarInteractor(repository);
    }
}
