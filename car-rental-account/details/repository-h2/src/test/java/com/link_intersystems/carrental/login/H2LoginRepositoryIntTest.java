package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.CarRentalDBExtension;
import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;
import com.link_intersystems.jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@CarRentalDBExtension
class H2LoginRepositoryIntTest {

    private H2LoginRepository loginRepository;

    @BeforeEach
    void setUp(Connection connection) {
        loginRepository = new H2LoginRepository(new JdbcTemplate(() -> connection));
    }

    @Test
    void isLoginExistent() {
        Customer customer = loginRepository.findUser(new Login("rene.link", new SecurePassword("rene".toCharArray())));

        assertNotNull(customer);
        assertEquals(new CustomerId(6), customer.getId());
        assertEquals("René", customer.getFirstname());
        assertEquals("Link", customer.getLastname());
    }

    @Test
    void isLogingNotExistent() {
        Customer customer = loginRepository.findUser(new Login("rene.link", new SecurePassword("link".toCharArray())));

        assertNull(customer);
    }
}