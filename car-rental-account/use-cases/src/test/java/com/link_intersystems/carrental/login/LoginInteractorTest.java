package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.time.ClockProvider;
import com.link_intersystems.carrental.time.EnableFixedClock;
import com.link_intersystems.carrental.time.FixedClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class LoginInteractorTest {

    private LoginInteractor loginInteractor;
    private LoginRepositoryMock loginRepository;

    @BeforeEach
    void setUp() {
        AccessTokenIssuer accessTokenIssuer = new AccessTokenIssuer(Duration.of(5, ChronoUnit.SECONDS), "abc"::toCharArray);
        loginRepository = new LoginRepositoryMock();

        loginInteractor = new LoginInteractor(loginRepository, accessTokenIssuer);
    }

    @Test
    @FixedClock("2023-05-12 05:25:42")
    void login() {
        loginRepository.addUser(2, "rene.link", "12345",  "René", "Link", "CUSTOMER", "MANAGER");

        LoginResponseModel loginResponseModel = loginInteractor.login("rene.link", "12345".toCharArray());

        assertNotNull(loginResponseModel);
        assertNotNull(loginResponseModel.getTokenSignature());
        assertEquals("rene.link", loginResponseModel.getUsername());
        assertEquals("René", loginResponseModel.getFirstname());
        assertEquals("Link", loginResponseModel.getLastname());
        assertEquals(2, loginResponseModel.getUserId());
        assertEquals(Arrays.asList("CUSTOMER", "MANAGER"), loginResponseModel.getRoles());
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