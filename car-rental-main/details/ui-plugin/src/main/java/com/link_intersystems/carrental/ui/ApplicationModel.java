package com.link_intersystems.carrental.ui;

import com.link_intersystems.carrental.login.ui.UserModel;

public class ApplicationModel {
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
