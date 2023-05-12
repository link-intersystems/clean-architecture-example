package com.link_intersystems.carrental.login;

import java.util.Objects;

import static java.util.Objects.*;

public class Signature {

    private String value;

    Signature(String value) {
        this.value = requireNonNull(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        return Objects.equals(value, signature.value);
    }

    @Override
    public int hashCode() {
        return hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
