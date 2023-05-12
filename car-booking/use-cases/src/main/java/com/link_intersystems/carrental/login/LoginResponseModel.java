package com.link_intersystems.carrental.login;

public class LoginResponseModel {
    private String tokenSignature;
    private String username;
    private long tokenIssueTime;

    public String getTokenSignature() {
        return tokenSignature;
    }

    public void setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setTokenIssueTime(long tokenIssueTime) {
        this.tokenIssueTime = tokenIssueTime;
    }

    public long getTokenIssueTime() {
        return tokenIssueTime;
    }
}
