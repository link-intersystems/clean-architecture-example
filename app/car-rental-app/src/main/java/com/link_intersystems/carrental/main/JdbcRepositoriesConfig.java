package com.link_intersystems.carrental.main;

import javax.sql.DataSource;

public class JdbcRepositoriesConfig {

    private DataSource dataSource;

    public JdbcRepositoriesConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
