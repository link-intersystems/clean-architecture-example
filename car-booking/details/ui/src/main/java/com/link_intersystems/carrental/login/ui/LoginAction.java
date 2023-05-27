package com.link_intersystems.carrental.login.ui;

import com.link_intersystems.carrental.application.ui.ApplicationModel;
import com.link_intersystems.carrental.application.ui.ApplicationView;
import com.link_intersystems.carrental.application.ui.CustomerModel;
import com.link_intersystems.carrental.login.LoginResponseModel;
import com.link_intersystems.carrental.login.LoginUseCase;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static java.util.Objects.*;

public class LoginAction extends AbstractTaskAction<LoginResponseModel, LoginResponseModel> {

    private LoginUseCase loginUseCase;

    private CustomerModel customerModel = new CustomerModel();
    private LoginModel loginModel = new LoginModel();
    private Optional<LoginView> loginView = Optional.empty();
    private Optional<Supplier<ApplicationView>> applicationViewSupplier = Optional.empty();

    public LoginAction(LoginUseCase loginUseCase) {

        this.loginUseCase = requireNonNull(loginUseCase);
        putValue(Action.NAME, "Login");
    }

    public void setApplicationViewSupplier(Supplier<ApplicationView> applicationViewSupplier) {
        this.applicationViewSupplier = Optional.ofNullable(applicationViewSupplier);
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    @Override
    protected LoginResponseModel doInBackground(TaskProgress<LoginResponseModel> taskProgress) {
        LoginModel loginModel = getLoginModel();
        loginModel.setLoginFailed(false);
        String username = loginModel.getUsername();
        char[] password = loginModel.getPassword();

        return loginUseCase.login(username, password);
    }

    public void setLoginView(LoginView loginView) {
        this.loginView.ifPresent(lv -> {
            lv.setModel(new LoginModel());
            lv.setLoginAction(null);
        });
        this.loginView = Optional.ofNullable(loginView);
        this.loginView.ifPresent(lv -> {
            lv.setModel(getLoginModel());
            lv.setLoginAction(LoginAction.this);
        });
    }

    @Override
    protected void done(LoginResponseModel loginResponse) {

        loginModel.setLoginFailed(!loginResponse.isSuccessful());

        if (loginResponse.isSuccessful()) {
            customerModel.setId(loginResponse.getCustomerId());
            customerModel.setFirstname(loginResponse.getCustomerFirstname());
            customerModel.setLastname(loginResponse.getCustomerLastname());

            loginView.ifPresent(LoginView::close);
            loginView = Optional.empty();

            Optional<ApplicationView> applicationView = applicationViewSupplier.map(Supplier::get);
            applicationView.ifPresent(v -> openApplication(v));
        } else {
            loginView.ifPresent(LoginView::update);
        }
    }

    private void openApplication(ApplicationView applicationView) {
        ApplicationModel applicationModel = new ApplicationModel();
        applicationModel.setCustomerModel(customerModel);
        applicationView.setModel(applicationModel);
        applicationView.show();
    }

    @Override
    protected void failed(ExecutionException e) {
        customerModel.clear();


        loginModel.setLoginException(e.getCause());
    }

    @Override
    protected void doFinally() {
        getLoginModel().clear();
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }
}
