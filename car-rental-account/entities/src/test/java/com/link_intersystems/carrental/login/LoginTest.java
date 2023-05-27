package com.link_intersystems.carrental.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void getter() {
        SecurePassword securePassword = new SecurePassword("test".toCharArray());
        Login login = new Login("rene.link", securePassword);

        assertEquals("rene.link", login.getUsername());
        assertEquals(securePassword, login.getSecurePassword());
    }

}