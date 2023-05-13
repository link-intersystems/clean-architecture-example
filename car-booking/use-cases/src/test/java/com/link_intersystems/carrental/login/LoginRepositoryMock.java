package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.customer.Customer;
import com.link_intersystems.carrental.customer.CustomerId;

import java.util.LinkedHashMap;
import java.util.Map;

class LoginRepositoryMock implements LoginRepository {

    private Map<Login, Customer> logins = new LinkedHashMap<>();

    @Override
    public Customer findCustomer(Login login) {
        for (Map.Entry<Login, Customer> entry : logins.entrySet()) {
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
        Customer customer = new Customer(new CustomerId(customerId), customerFirstname, customerLastname);
        logins.put(login, customer);
    }
}
