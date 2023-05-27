package com.link_intersystems.carrental.login;

import com.link_intersystems.jdbc.JdbcTemplate;

import java.time.Duration;

public class LoginComponent {
    public LoginUseCase createLoginUseCase(JdbcTemplate jdbcTemplate) {
        AccessTokenIssuer accessTokenIssuer = new AccessTokenIssuer(Duration.ofHours(1), "1234"::toCharArray);

        LoginRepository loginRepository = new H2LoginRepository(jdbcTemplate);
        return new LoginInteractor(loginRepository, accessTokenIssuer);
    }
}
