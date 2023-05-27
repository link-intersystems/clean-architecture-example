package com.link_intersystems.carrental.login;

import com.link_intersystems.carrental.time.ClockProvider;

import java.time.*;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.*;
import static java.util.Objects.*;

public class AccessTokenIssuer {

    private Duration expirationTime;
    private SignatureFactory signatureFactory;

    public AccessTokenIssuer(Duration expirationTime, Supplier<char[]> signingSecretSupplier) {
        this.expirationTime = requireNonNull(expirationTime);
        this.signatureFactory = new SignatureFactory(signingSecretSupplier);
    }

    public AccessToken issueAccessToken(String userId) {
        IssueTime issueTime =  new IssueTime(ClockProvider.now());
        Signature signature = signatureFactory.createSignature(userId + issueTime.getEpochMilli());
        return new AccessToken(userId, issueTime, signature);
    }


    public boolean isValid(AccessToken accessToken) {
        Signature signatureToVerify = accessToken.getSignature();
        String userId = accessToken.getUserId();
        long timeOfIssue = accessToken.getIssueTime().getEpochMilli();
        Signature signature = signatureFactory.createSignature(userId + timeOfIssue);

        if (!signatureToVerify.equals(signature)) {
            return false;
        }

        LocalDateTime expirationDate = getExpirationDate(timeOfIssue);
        LocalDateTime now = ClockProvider.now();
        return now.isBefore(expirationDate);
    }

    protected LocalDateTime getExpirationDate(long timeOfIssue) {
        Instant instant = Instant.ofEpochMilli(timeOfIssue);
        LocalDateTime timeOfIssueDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return timeOfIssueDate.plus(expirationTime.toMillis(), MILLIS);
    }
}
