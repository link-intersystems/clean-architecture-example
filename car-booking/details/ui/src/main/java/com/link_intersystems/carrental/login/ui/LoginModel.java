package com.link_intersystems.carrental.login.ui;

import java.util.Arrays;

import static java.util.Objects.*;

public class LoginModel {
    private String username = "";
    private char[] password = new char[0];

    public void clear() {
        Arrays.fill(password, '\0');
        password = new char[0];
        this.username = "";
    }

    public void setUsername(String username) {
        this.username = requireNonNull(username);
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(char[] password) {
        this.password = requireNonNull(password);
    }

    public char[] getPassword() {
        return password.clone();
    }
}
