package com.link_intersystems.carrental.login;

import java.time.LocalDateTime;

import static java.util.Objects.*;

public class AccessToken {
    private String userId;
    private Signature signature;
    private IssueTime issueTime;

    public AccessToken(String userId, IssueTime issueTime, Signature signature) {
        this.userId = requireNonNull(userId);
        this.issueTime = requireNonNull(issueTime);
        this.signature = requireNonNull(signature);
    }

    public String getUserId() {
        return userId;
    }

    public IssueTime getIssueTime() {
        return issueTime;
    }

    public Signature getSignature() {
        return signature;
    }
}
