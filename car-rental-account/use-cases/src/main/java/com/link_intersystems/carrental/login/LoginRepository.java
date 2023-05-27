package com.link_intersystems.carrental.login;


public interface LoginRepository {
    User findUser(Login login);
}
