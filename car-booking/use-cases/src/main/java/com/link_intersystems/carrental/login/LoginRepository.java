package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.customer.Customer;

public interface LoginRepository {
    Customer findCustomer(Login login);
}
