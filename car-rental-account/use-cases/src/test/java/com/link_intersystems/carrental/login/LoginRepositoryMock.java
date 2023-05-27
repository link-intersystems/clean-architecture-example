package com.link_intersystems.carrental.login;

import java.util.LinkedHashMap;
import java.util.Map;

class LoginRepositoryMock implements LoginRepository {

    private Map<Login, User> logins = new LinkedHashMap<>();

    @Override
    public User findUser(Login login) {
        for (Map.Entry<Login, User> entry : logins.entrySet()) {
            Login aLogin = entry.getKey();
            if (equals(login, aLogin)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private boolean equals(Login login, Login otherLogin) {
        return login.getUsername().equals(otherLogin.getUsername()) &&
                login.getSecurePassword().getValue().equals(otherLogin.getSecurePassword().getValue());
    }

    public void addUser(String username, String securePassword, int customerId, String customerFirstname, String customerLastname) {
        Login login = new Login(username, new SecurePassword(securePassword.toCharArray()));
        User customer = new User();
        logins.put(login, customer);
    }
}
