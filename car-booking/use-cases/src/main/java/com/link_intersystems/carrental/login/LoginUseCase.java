package com.link_intersystems.carrental.login;

public interface LoginUseCase {

    public LoginResponseModel login(String username, char[] password);
}
