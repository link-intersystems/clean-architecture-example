package com.link_intersystems.carrental.management.rental.returnCar;

import com.link_intersystems.jdbc.JdbcTemplate;

import javax.sql.DataSource;

public class ReturnCarComponent {

    private final DataSource dataSource;

    public ReturnCarComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ReturnCarUseCase getReturnUseCase() {
        JdbcTemplate managementJdbcTemplate = new JdbcTemplate(dataSource, "MANAGEMENT");
        ReturnCarRepository repository = new H2ReturnCarRepository(managementJdbcTemplate);
        return new ReturnCarInteractor(repository);
    }
}
