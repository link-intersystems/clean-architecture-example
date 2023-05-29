package com.link_intersystems.carrental.login;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public class LoginResponseModel {
    private String tokenSignature;
    private long tokenIssueTime;

    private String username;
    private String firstname;
    private String lastname;

    private int userId = -1;
    private List<String> roles = new ArrayList<>();

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        return userId > -1 && tokenSignature != null && tokenIssueTime != 0;
    }

    public List<String> getRoles() {
        return new ArrayList<>(roles);
    }

    public void setRoles(List<String> roles) {
        this.roles = requireNonNull(roles);
    }
}
