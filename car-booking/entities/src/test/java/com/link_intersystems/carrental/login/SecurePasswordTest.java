package com.link_intersystems.carrental.login;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordTest {

    @Test
    void getValue() {
        char[] password = "HelloWorld".toCharArray();
        SecurePassword securePassword = new SecurePassword(password);

        assertNotNull(securePassword);
        assertNotEquals("HelloWorld", securePassword.getValue());
        assertEquals("872e4e50ce9990d8b041330c47c9ddd11bec6b503ae9386a99da8584e9bb12c4", securePassword.getValue());
        assertNotEquals("\0\0\0\0\0\0\0\0\0\0\0\0\0", new String(password));
    }
}