package com.link_intersystems.carrental.login;

public class LoginResponseModel {
    private String tokenSignature;
    private String username;
    private long tokenIssueTime;

    private int customerId = -1;
    private String customerFirstname;
    private String customerLastname;

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

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public boolean isSuccessful() {
        return customerId > -1 && tokenSignature != null && tokenIssueTime != 0;
    }
}
