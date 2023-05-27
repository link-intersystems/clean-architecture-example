package com.link_intersystems.carrental.login.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Supplier;

public class InitiateLoginAction extends AbstractAction {

    private Supplier<LoginView> loginViewSupplier;
    private LoginAction loginAction;

    public InitiateLoginAction(LoginAction loginAction, Supplier<LoginView> loginViewSupplier) {
        this.loginAction = loginAction;
        this.loginViewSupplier = loginViewSupplier;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        LoginView loginView = loginViewSupplier.get();
        loginAction.setLoginView(loginView);
        loginView.show();
    }
}
