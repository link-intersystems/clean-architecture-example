package com.link_intersystems.carrental.login.ui;

import javax.swing.*;

public class LoginViewMock implements LoginView {
    private LoginModel loginModel;
    private Action loginAction;

    @Override
    public void setLoginAction(Action loginAction) {
        this.loginAction = loginAction;
    }

    public Action getLoginAction() {
        return loginAction;
    }

    @Override
    public void close() {

    }

    @Override
    public void show() {

    }

    @Override
    public void setModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    @Override
    public void update() {
    }

    public LoginModel getModel() {
        return loginModel;
    }
}
