package com.link_intersystems.carrental.login;

import com.link_intersystems.jdbc.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.*;

public class H2LoginRepository implements LoginRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2LoginRepository(JdbcTemplate carRentalJdbcTemplate) {
        this.jdbcTemplate = requireNonNull(carRentalJdbcTemplate);
    }

    @Override
    public User findUser(Login login) {
        return jdbcTemplate.query(c -> {
            PreparedStatement ps = c.prepareStatement("""
                                select c.* from login l 
                                    join customer c on c.id = l.customer_id
                                    where l.username = ? 
                                    and l.pass = ? 
                    """);
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getSecurePassword().getValue());


            ResultSet resultSet = ps.executeQuery();
            if (resultSet != null && resultSet.next()) {
                return mapCustomer(resultSet);
            }
            return null;
        });
    }

    private User mapCustomer(ResultSet resultSet) throws SQLException {
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");

        return new User();
    }
}
