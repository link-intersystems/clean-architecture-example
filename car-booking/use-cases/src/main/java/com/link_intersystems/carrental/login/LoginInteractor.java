package com.link_intersystems.carrental.login;

import static java.util.Objects.*;

public class LoginInteractor implements LoginUseCase {
    private AccessTokenIssuer accessTokenIssuer;
    private LoginRepository repository;

    public LoginInteractor(LoginRepository repository, AccessTokenIssuer accessTokenIssuer) {
        this.accessTokenIssuer = requireNonNull(accessTokenIssuer);
        this.repository = requireNonNull(repository);
    }

    @Override
    public LoginResponseModel login(String username, char[] password) {
        SecurePassword securePassword = new SecurePassword(password);
        Login login = new Login(username, securePassword);

        LoginResponseModel response = new LoginResponseModel();

        if (repository.isLoginExistent(login)) {
            AccessToken accessToken = accessTokenIssuer.issueAccessToken(username);
            updateAccessToken(response, accessToken);
        }

        return response;
    }

    private void updateAccessToken(LoginResponseModel loginModel, AccessToken accessToken) {
        Signature signature = accessToken.getSignature();
        loginModel.setTokenSignature(signature.toString());
        loginModel.setUsername(accessToken.getUserId());
        IssueTime issueTime = accessToken.getIssueTime();
        loginModel.setTokenIssueTime(issueTime.getEpochMilli());
    }
}
