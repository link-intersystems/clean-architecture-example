package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.application.ui.ApplicationView;
import com.link_intersystems.carrental.login.ui.DefaultLoginView;
import com.link_intersystems.carrental.login.ui.InitiateLoginAction;
import com.link_intersystems.carrental.login.ui.LoginAction;
import com.link_intersystems.jdbc.JdbcTemplate;

import java.util.function.Supplier;

public class LoginViewConfig {
    private DefaultLoginView loginView;

    private LoginAction loginAction;
    private JdbcTemplate jdbcTemplate;
    private Supplier<ApplicationView> applicationViewSupplier;
    private InitiateLoginAction initiateLoginAction;

    public LoginViewConfig(JdbcTemplate jdbcTemplate, Supplier<ApplicationView> applicationViewSupplier) {
        this.jdbcTemplate = jdbcTemplate;
        this.applicationViewSupplier = applicationViewSupplier;
    }

    public DefaultLoginView getLoginView() {
        if (loginView == null) {
            loginView = new DefaultLoginView();
        }
        return loginView;
    }

    public LoginAction getLoginController() {
        if (loginAction == null) {
            LoginUseCase loginUseCase = createLoginUseCase();
            loginAction = new LoginAction(loginUseCase);
        }
        return loginAction;
    }

    public InitiateLoginAction getInitiateLoginAction() {
        if (initiateLoginAction == null) {
            initiateLoginAction = new InitiateLoginAction(getLoginController(), this::getLoginView);
        }
        return initiateLoginAction;
    }

    private LoginUseCase createLoginUseCase() {
        LoginComponent loginComponent = new LoginComponent();
        return loginComponent.createLoginUseCase(jdbcTemplate);
    }
}
