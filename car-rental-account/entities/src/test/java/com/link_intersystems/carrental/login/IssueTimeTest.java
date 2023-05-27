package com.link_intersystems.carrental.login;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IssueTimeTest {

    @Test
    void getEpochMilli() {
        long now = System.currentTimeMillis();
        IssueTime issueTime = new IssueTime(now);
        assertEquals(now, issueTime.getEpochMilli());
    }

    @Test
    void getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        IssueTime issueTime = new IssueTime(now);
        assertEquals(now, issueTime.getDateTime());
    }
}