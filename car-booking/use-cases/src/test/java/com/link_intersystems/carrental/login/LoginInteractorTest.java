package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.EnableFixedClock;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class LoginInteractorTest {

    private LoginInteractor loginInteractor;
    private LoginRepositoryMock customerLoginRepository;

    @BeforeEach
    void setUp() {
        AccessTokenIssuer accessTokenIssuer = new AccessTokenIssuer(Duration.of(5, ChronoUnit.SECONDS), "abc"::toCharArray);
        customerLoginRepository = new LoginRepositoryMock();

        loginInteractor = new LoginInteractor(customerLoginRepository, accessTokenIssuer);
    }

    @Test
    @FixedClock("2023-05-12 05:25:42")
    void login() {
        customerLoginRepository.addUser("rene.link", "12345");

        LoginResponseModel loginResponseModel = loginInteractor.login("rene.link", "12345".toCharArray());

        assertNotNull(loginResponseModel);
        assertNotNull(loginResponseModel.getTokenSignature());
        assertEquals("rene.link", loginResponseModel.getUsername());
        assertEquals(ClockProvider.getClock().millis(), loginResponseModel.getTokenIssueTime());
    }

    @Test
    @FixedClock("2023-05-12 05:25:42")
    void loginNonExistentUser() {
        LoginResponseModel loginResponseModel = loginInteractor.login("rene.link", "12345".toCharArray());

        assertNotNull(loginResponseModel);
        assertNull(loginResponseModel.getTokenSignature());
        assertNull(loginResponseModel.getUsername());
        assertEquals(0, loginResponseModel.getTokenIssueTime());
    }
}