package com.link_intersystems.carrental.login;

import static java.util.Arrays.*;

public class SecurePassword {

    private final String value;

    public SecurePassword(char[] password) {
        try {
            Hash hash = new MessageDigestHash();
            this.value = hash.hash(password);
        } finally {
            fill(password, '\0');
        }
    }

    public String getValue() {
        return value;
    }

}
