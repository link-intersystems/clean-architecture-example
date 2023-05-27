package com.link_intersystems.carrental.login;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.*;

public class MessageDigestHash implements Hash {

    private MessageDigest md;

    public MessageDigestHash() {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }

    @Override
    public String hash(CharSequence text) {
        md.reset();
        byte[] bytes = toBytes(text);
        md.update(bytes);
        byte[] hash = md.digest();
        return bytesToHex(hash);
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int bytesIndex = 0; bytesIndex < bytes.length; bytesIndex++) {
            int v = bytes[bytesIndex] & 0xFF;
            int hexIndex = bytesIndex * 2;
            hexChars[hexIndex] = HEX_ARRAY[v >>> 4];
            hexChars[hexIndex + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private byte[] toBytes(CharSequence charSequence) {
        return toBytes(charSequence, UTF_8);
    }

    private byte[] toBytes(CharSequence charSequence, Charset charset) {

        CharBuffer charBuffer = toCharBuffer(charSequence);
        ByteBuffer byteBuffer = charset.encode(charBuffer);

        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(byteBuffer.position(), bytes);
        return bytes;
    }

    private CharBuffer toCharBuffer(CharSequence charSequence) {
        if (CharBuffer.class.isInstance(charSequence)) {
            return CharBuffer.class.cast(charSequence);
        }

        return CharBuffer.wrap(charSequence);
    }
}
