package com.link_intersystems.carrental.login.ui;


import javax.swing.*;

public interface LoginView {
    void setLoginAction(Action action);

    void close();

    void show();

    void setModel(LoginModel loginModel);

    void update();
}
