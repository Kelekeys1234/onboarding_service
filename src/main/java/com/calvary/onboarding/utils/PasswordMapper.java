package com.calvary.onboarding.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordMapper {

    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);  // Use Base64 encoding
    }

    public String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt)); // Decode salt properly
            md.update(password.getBytes());  // Hash password after updating salt
            byte[] hashedPassword = md.digest();
            return Base64.getEncoder().encodeToString(hashedPassword);  // Use Base64 encoding for consistency
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password.");
        }
    }

}
