package com.link_intersystems.carrental.login;

import java.nio.CharBuffer;

public interface Hash {

    default String hash(char[] text) {
        return hash(CharBuffer.wrap(text));
    }

    String hash(CharSequence text);
}
