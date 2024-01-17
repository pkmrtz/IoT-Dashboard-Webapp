package com.webapps.telemetrywebapp.features;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureTokenGenerator {

    private static final int TOKEN_LENGTH = 16;

    public static String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}

