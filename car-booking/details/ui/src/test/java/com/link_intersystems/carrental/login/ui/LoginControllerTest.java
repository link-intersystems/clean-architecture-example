package com.link_intersystems.carrental.login.ui;

import com.link_intersystems.carrental.login.LoginResponseModel;
import com.link_intersystems.carrental.login.LoginUseCase;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.DirectTaskExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginUseCaseMock useCaseMock;
    private LoginController loginController;
    private ActionTrigger actionTrigger;

    @BeforeEach
    void setUp() {
        useCaseMock = new LoginUseCaseMock();
        loginController = new LoginController(useCaseMock);
        loginController.setTaskExecutor(new DirectTaskExecutor());

        actionTrigger = new ActionTrigger(this);
    }

    @Test
    void login(){
        LoginResponseModel loginResponseModel = new LoginResponseModel();
        loginResponseModel.setCustomerId(6);
        loginResponseModel.setCustomerFirstname("René");
        loginResponseModel.setCustomerLastname("Link");
        loginResponseModel.setTokenIssueTime(12345);
        loginResponseModel.setTokenSignature("abcde");
        useCaseMock.whenLogin("rene.link", "rene".toCharArray()).thenReturn(loginResponseModel);

        LoginModel loginModel = loginController.getLoginModel();

        loginModel.setUsername("rene.link");
        char[] loginPassword = "rene".toCharArray();
        loginModel.setPassword(loginPassword);

        actionTrigger.performAction(loginController);

        assertEquals("", loginModel.getUsername(), "username cleared");
        assertArrayEquals(new char[]{'\0','\0','\0','\0'}, loginPassword, "login password cleared");
        assertArrayEquals(new char[0], loginModel.getPassword(), "cleared model contains empty password");

        LoggedInCustomerModel customerModel = loginController.getCustomerModel();
        assertEquals(6, customerModel.getId());
        assertEquals("René", customerModel.getFirstname());
        assertEquals("Link", customerModel.getLastname());
    }
}