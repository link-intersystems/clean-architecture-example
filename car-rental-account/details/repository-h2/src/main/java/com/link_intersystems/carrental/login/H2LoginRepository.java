package com.link_intersystems.carrental.login;

import com.link_intersystems.jdbc.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                                select l.* from login l 
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
        UserId userId = new UserId(resultSet.getInt("id"));
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");

        List<String> roles = new ArrayList<>();
        String roleNameValue = resultSet.getString("roles");
        String[] roleNames = roleNameValue.split(",");
        roles.addAll(Arrays.asList(roleNames));
        return new User(userId, firstname, lastname, roles);
    }
}
