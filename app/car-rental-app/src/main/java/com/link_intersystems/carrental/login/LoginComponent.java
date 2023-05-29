package com.link_intersystems.carrental.login;

import com.link_intersystems.jdbc.JdbcTemplate;

import java.time.Duration;

public class LoginComponent {
    public LoginUseCase createLoginUseCase(JdbcTemplate accountTemplate) {
        AccessTokenIssuer accessTokenIssuer = new AccessTokenIssuer(Duration.ofHours(1), "1234"::toCharArray);

        LoginRepository loginRepository = new H2LoginRepository(accountTemplate);
        return new LoginInteractor(loginRepository, accessTokenIssuer);
    }
}
