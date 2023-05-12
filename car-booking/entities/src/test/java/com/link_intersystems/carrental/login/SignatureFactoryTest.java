package com.link_intersystems.carrental.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignatureFactoryTest {

    private SignatureFactory signatureFactory;
    private char[] signingSecret;

    @BeforeEach
    void setUp(){
        signingSecret = "12345".toCharArray();
        signatureFactory = new SignatureFactory(() -> signingSecret);
    }

    @Test
    void sign() {
        Signature signature = signatureFactory.createSignature("Hello");

        assertEquals("67698a29126e52a6921ca061082783ede0e9085c45163c3658a2b0a82c8f95a1", signature.toString());
        assertArrayEquals("\0\0\0\0\0".toCharArray(), signingSecret, "Signing secret should be erased after use.");
    }

}