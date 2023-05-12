package com.link_intersystems.carrental.login;

import java.util.Arrays;
import java.util.function.Supplier;

import static java.util.Objects.*;

class SignatureFactory {

    private Hash hash;
    private Supplier<char[]> signingSecretSupplier;

    SignatureFactory(Supplier<char[]> signingSecretSupplier) {
        this(new MessageDigestHash(), signingSecretSupplier);
    }

    SignatureFactory(Hash hash, Supplier<char[]> signingSecretSupplier) {
        this.hash = requireNonNull(hash);
        this.signingSecretSupplier = requireNonNull(signingSecretSupplier);
    }

    public Signature createSignature(Object object) {
        char[] rawSignature = createRawSignature(object);
        String signatureHash = hash.hash(rawSignature);
        Arrays.fill(rawSignature, '\0');
        return new Signature(signatureHash);
    }

    private char[] createRawSignature(Object object) {
        char[] objectChars = String.valueOf(object).toCharArray();
        char[] signingSecret = signingSecretSupplier.get();
        char[] signature = new char[objectChars.length + signingSecret.length];
        System.arraycopy(objectChars, 0, signature, 0, objectChars.length);
        System.arraycopy(signingSecret, 0, signature, objectChars.length, signingSecret.length);
        Arrays.fill(signingSecret, '\0');
        return signature;
    }

}
