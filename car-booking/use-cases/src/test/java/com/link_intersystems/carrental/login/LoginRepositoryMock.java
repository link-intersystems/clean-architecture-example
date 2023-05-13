package com.link_intersystems.carrental.login;

import java.util.LinkedHashSet;
import java.util.Set;

class LoginRepositoryMock implements LoginRepository {

    private Set<Login> logins = new LinkedHashSet<>();

    @Override
    public boolean isLoginExistent(Login login) {
        for (Login aLoging : logins) {
            if (equals(login, aLoging)) {
                return true;
            }
        }
        return false;
    }

    private boolean equals(Login login, Login otherLogin) {
        return login.getUsername().equals(otherLogin.getUsername()) &&
                login.getSecurePassword().getValue().equals(otherLogin.getSecurePassword().getValue());
    }

    public void addUser(String username, String securePassword) {
        logins.add(new Login(username, new SecurePassword(securePassword.toCharArray())));
    }
}
