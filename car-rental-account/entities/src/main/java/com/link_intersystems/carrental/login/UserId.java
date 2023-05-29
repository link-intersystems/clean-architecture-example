package com.link_intersystems.carrental.login;

public class UserId {

    private int value;

    public UserId(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("userId must be greater then 0");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
