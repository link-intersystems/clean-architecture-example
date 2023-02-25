package com.link_intersystems.carrental;

import com.link_intersystems.plugins.AbstractServiceProvider;
import com.link_intersystems.plugins.ApplicationContext;
import com.link_intersystems.sql.io.InputStreamScriptResource;
import com.link_intersystems.sql.io.SqlScript;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.function.BiConsumer;

public class H2DataSourceServiceProvider extends AbstractServiceProvider {

    @Override
    protected void doInit(ApplicationContext applicationContext, BiConsumer<Class<?>, Object> registerService) {
        StringBuilder urlBuilder = new StringBuilder("jdbc:h2:file:./car-rental");
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(urlBuilder.toString());
        jdbcDataSource.setUser("");
        jdbcDataSource.setPassword("");

        if (!new File("./car-rental.mv.db").exists()) {


            try (Connection connection = jdbcDataSource.getConnection()) {
                InputStream resourceAsStream = H2DataSourceServiceProvider.class.getResourceAsStream("/com/link_intersystems/carrental/offers/init.sql");
                SqlScript sqlScript = new SqlScript(new InputStreamScriptResource(resourceAsStream));
                sqlScript.execute(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        registerService.accept(DataSource.class, jdbcDataSource);
    }

    @Override
    protected void initProvidedServiceType(Set<Class<?>> providedServices) {
        providedServices.add(DataSource.class);
    }
}
