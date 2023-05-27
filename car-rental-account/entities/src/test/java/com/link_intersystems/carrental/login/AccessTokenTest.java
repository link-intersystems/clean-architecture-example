package com.link_intersystems.carrental.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenTest {

    private AccessToken accessToken;
    private IssueTime timeOfIssue;

    @BeforeEach
    void setUp() {
        timeOfIssue = new IssueTime(System.currentTimeMillis());
        accessToken = new AccessToken("rene.link", timeOfIssue, new Signature("abc123"));
    }

    @Test
    void getUserId() {
        assertEquals("rene.link", accessToken.getUserId());
    }

    @Test
    void getTimeOfIssue() {
        assertEquals(timeOfIssue, accessToken.getIssueTime());
    }

    @Test
    void getSignature() {
        assertEquals(new Signature("abc123"), accessToken.getSignature());
    }
}