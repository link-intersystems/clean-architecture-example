package com.link_intersystems.carrental.login;

import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;

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
        User user = loginRepository.findUser(new Login("rene.link", new SecurePassword("rene".toCharArray())));

        assertNotNull(user);
        assertEquals("René", user.getFirstname());
        assertEquals("Link", user.getLastname());
        assertEquals(Arrays.asList("CUSTOMER", "MANAGER"), user.getRoles());
    }

    @Test
    void isLogingNotExistent() {
        User customer = loginRepository.findUser(new Login("rene.link", new SecurePassword("link".toCharArray())));

        assertNull(customer);
    }
}
