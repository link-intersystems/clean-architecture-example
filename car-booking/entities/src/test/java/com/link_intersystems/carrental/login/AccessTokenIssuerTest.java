package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.time.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.*;
import static org.junit.jupiter.api.Assertions.*;

@EnableFixedClock
class AccessTokenIssuerTest {

    private AccessTokenIssuer accessTokenIssuer;

    @BeforeEach
    void setUp() {
        accessTokenIssuer = new AccessTokenIssuer(Duration.of(5, SECONDS), "abc"::toCharArray);
    }

    @Test
    @FixedClock(value = "2023-05-11 06:48:00", zoneId = "Europe/Berlin")
    void issueAccessToken() {
        System.out.println(ClockProvider.now());
        AccessToken accessToken = accessTokenIssuer.issueAccessToken("rene.link");

        assertNotNull(accessToken);
        assertEquals(new Signature("4a4c37d101dc73b1f00c036a34b2c0c01d1ebf4822795c6d0185d7ccb5d30d42"), accessToken.getSignature());
    }

    @Test
    @FixedClock(value = "2023-05-11 06:48:00")
    void isValid(MutableClock clock) {
        AccessToken accessToken = accessTokenIssuer.issueAccessToken("rene.link");

        assertTrue(accessTokenIssuer.isValid(accessToken));
        clock.roll(4, SECONDS);
        assertTrue(accessTokenIssuer.isValid(accessToken));
        clock.roll(1, SECONDS);
        assertFalse(accessTokenIssuer.isValid(accessToken));
    }

    @Test
    @FixedClock(value = "2023-05-11 06:48:00")
    void getExpirationDate() {
        LocalDateTime expirationDate = accessTokenIssuer.getExpirationDate(ClockProvider.getClock().millis());

        assertEquals(LocalDateTimeUtils.dateTime("2023-05-11", "06:48:05"), expirationDate);
    }
}