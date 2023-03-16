package com.link_intersystems.carrental.management.returnCar;

import com.link_intersystems.ioc.api.BeanSelector;
import org.springframework.jdbc.core.JdbcTemplate;

public class H2ReturnCarRepositoryConfig {

    public ReturnCarRepository getReturnCarRepository(BeanSelector<JdbcTemplate> jdbcTemplateBeanSelector) {
        return new H2ReturnCarRepository(jdbcTemplateBeanSelector.select("getManagementJdbcTemplate"));
    }
}
