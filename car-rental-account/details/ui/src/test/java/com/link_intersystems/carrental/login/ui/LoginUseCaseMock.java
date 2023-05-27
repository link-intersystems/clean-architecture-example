package com.link_intersystems.carrental.login.ui;

import com.link_intersystems.carrental.login.LoginResponseModel;
import com.link_intersystems.carrental.login.LoginUseCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginUseCaseMock implements LoginUseCase {

    public interface OngoingLogin {
        void thenReturn(LoginResponseModel loggedInCustomerModel);
    }

    private class LoginCall {

        private final String username;
        private final char[] password;

        public LoginCall(String username, char[] password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LoginCall loginCall = (LoginCall) o;
            return Objects.equals(username, loginCall.username) && Arrays.equals(password, loginCall.password);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(username);
            result = 31 * result + Arrays.hashCode(password);
            return result;
        }
    }

    private Map<LoginCall, LoginResponseModel> invocationResults = new HashMap<>();

    @Override
    public LoginResponseModel login(String username, char[] password) {
        LoginCall loginCall = new LoginCall(username, password);
        return invocationResults.get(loginCall);
    }

    public OngoingLogin whenLogin(String username, char[] password) {
        return (m) -> {
            LoginCall loginCall = new LoginCall(username, password);
            invocationResults.put(loginCall, m);
        };
    }
}
