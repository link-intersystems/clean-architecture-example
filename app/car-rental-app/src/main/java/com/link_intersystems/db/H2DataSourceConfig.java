package com.link_intersystems.db;

import com.link_intersystems.app.context.BeanSelector;
import com.link_intersystems.sql.io.SqlScript;
import com.link_intersystems.sql.io.StatementCallback;
import com.link_intersystems.sql.io.URLScriptResource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class H2DataSourceConfig {

    public DataSource getCarRentalDataSource() {
        ensureDatabaseInitialized();

        return createDataSource("jdbc:h2:file:./car-rental;USER=sa;PASSWORD=123;SCHEMA=RENTAL");
    }

    private void ensureDatabaseInitialized() {
        if (!new File("./car-rental.mv.db").exists()) {
            JdbcDataSource jdbcDataSource = createDataSource("jdbc:h2:file:./car-rental;USER=sa;PASSWORD=123");

            executeScript(jdbcDataSource, "/com/link_intersystems/carrental/init.sql");
            executeScript(jdbcDataSource, "/com/link_intersystems/carrental/management/management.sql");
        }
    }

    private static JdbcDataSource createDataSource(String jdbcUrl) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(jdbcUrl);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("123");
        return jdbcDataSource;
    }

    public DataSource getManagementDataSource() {
        ensureDatabaseInitialized();

        return createDataSource("jdbc:h2:file:./car-rental;USER=sa;PASSWORD=123;SCHEMA=MANAGEMENT");
    }

    private void executeScript(JdbcDataSource jdbcDataSource, String scriptResource) {
        try (Connection connection = jdbcDataSource.getConnection()) {
            URL scriptResourceURL = H2DataSourceConfig.class.getResource(scriptResource);
            SqlScript sqlScript = new SqlScript(new URLScriptResource(scriptResourceURL));
            sqlScript.execute(new StatementCallback() {
                @Override
                public void doWithStatement(String sqlStatement) throws SQLException {
                    System.out.println(sqlStatement);
                }
            });
            sqlScript.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JdbcTemplate getCarRentalJdbcTemplate(BeanSelector<DataSource> dataSources) {
        return new JdbcTemplate(dataSources.select("getCarRentalDataSource"));
    }

    public JdbcTemplate getManagementJdbcTemplate(BeanSelector<DataSource> dataSources) {
        return new JdbcTemplate(dataSources.select("getManagementDataSource"));
    }
}
