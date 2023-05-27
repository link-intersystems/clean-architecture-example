package com.link_intersystems.carrental.login;

public class LoginResponseModel {
    private String tokenSignature;
    private long tokenIssueTime;

    private String username;
    private String firstname;
    private String lastname;

    private int customerId = -1;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isSuccessful() {
        return customerId > -1 && tokenSignature != null && tokenIssueTime != 0;
    }
}
