package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.jdbc.JdbcTemplate;

public class ReturnCarComponent {

    public ReturnCarUseCase getReturnUseCase(JdbcTemplate managementJdbcTemplate) {
        ReturnCarRepository repository = new H2ReturnCarRepository(managementJdbcTemplate);
        return new ReturnCarInteractor(repository);
    }
}
