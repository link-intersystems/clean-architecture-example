package com.link_intersystems.carrental.login;

import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@CarRentalAccountDBExtension
class H2LoginRepositoryIntTest {

    private H2LoginRepository loginRepository;

    @BeforeEach
    void setUp(Connection connection) {
        loginRepository = new H2LoginRepository(new JdbcTemplate(() -> connection));
    }

    @Test
    void isLoginExistent() {
        User customer = loginRepository.findUser(new Login("rene.link", new SecurePassword("rene".toCharArray())));

        assertNotNull(customer);
    }

    @Test
    void isLogingNotExistent() {
        User customer = loginRepository.findUser(new Login("rene.link", new SecurePassword("link".toCharArray())));

        assertNull(customer);
    }
}