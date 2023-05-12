package com.link_intersystems.carrental.login;

import static java.util.Objects.*;

public class Login {

    private String username;
    private SecurePassword securePassword;

    public Login(String username, SecurePassword securePassword) {
        this.username = requireNonNull(username);
        this.securePassword = requireNonNull(securePassword);
    }

    public String getUsername() {
        return username;
    }

    public SecurePassword getSecurePassword() {
        return securePassword;
    }
}
