package com.link_intersystems.carrental.login.ui;

import com.link_intersystems.carrental.login.LoginResponseModel;
import com.link_intersystems.carrental.login.LoginUseCase;
import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.*;

public class LoginAction extends AbstractTaskAction<LoginResponseModel, LoginResponseModel> {

    private LoginUseCase loginUseCase;

    private LoginModel loginModel = new LoginModel();
    private Optional<LoginView> loginView = Optional.empty();
    private Optional<ActionListener> onSuccessfulLoginAction = Optional.empty();
    private ActionTrigger actionTrigger = new ActionTrigger(this);

    public LoginAction(LoginUseCase loginUseCase) {

        this.loginUseCase = requireNonNull(loginUseCase);
        putValue(Action.NAME, "Login");
    }

    public void setOnSuccessfulLoginAction(ActionListener onSuccessfulLoginAction) {
        this.onSuccessfulLoginAction = Optional.ofNullable(onSuccessfulLoginAction);
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

            loginView.ifPresent(LoginView::close);
            loginView = Optional.empty();

            onSuccessfulLoginAction.ifPresent(actionTrigger::performAction);
        } else {
            loginView.ifPresent(LoginView::update);
        }
    }

    @Override
    protected void failed(ExecutionException e) {
        loginModel.setLoginException(e.getCause());
    }

    @Override
    protected void doFinally() {
        getLoginModel().clear();
    }

}
