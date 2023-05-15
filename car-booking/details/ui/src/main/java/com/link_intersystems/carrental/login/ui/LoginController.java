package com.link_intersystems.carrental.login.ui;

import com.link_intersystems.carrental.login.LoginResponseModel;
import com.link_intersystems.carrental.login.LoginUseCase;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.*;

public class LoginController extends AbstractTaskAction<LoginResponseModel, LoginResponseModel> {

    private LoginUseCase loginUseCase;

    private LoggedInCustomerModel loggedInCustomerModel = new LoggedInCustomerModel();
    private LoginModel loginModel = new LoginModel();

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = requireNonNull(loginUseCase);
    }

    @Override
    protected LoginResponseModel doInBackground(TaskProgress<LoginResponseModel> taskProgress) {
        String username = loginModel.getUsername();
        char[] password = loginModel.getPassword();

        return loginUseCase.login(username, password);
    }

    @Override
    protected void done(LoginResponseModel loginResponse) {
        loginModel.clear();

        if (loginResponse.isSuccessful()) {
            loggedInCustomerModel.setId(loginResponse.getCustomerId());
            loggedInCustomerModel.setFirstname(loginResponse.getCustomerFirstname());
            loggedInCustomerModel.setLastname(loginResponse.getCustomerLastname());
        }
    }

    @Override
    protected void failed(ExecutionException e) {
        loggedInCustomerModel.clear();
        loginModel.clear();
    }

    public LoggedInCustomerModel getCustomerModel() {
        return loggedInCustomerModel;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }


}
