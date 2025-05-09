package com.marketplace.backend.util;

import jakarta.xml.bind.DatatypeConverter;
import org.mindrot.jbcrypt.BCrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtil {

    private static final Logger log = LoggerFactory.getLogger(PasswordUtil.class);

    /**
     * Gera um salt compatível com BCrypt.
     *
     * @return Um salt BCrypt (e.g., $2a$12$...).
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);

        return DatatypeConverter.printHexBinary(salt).toUpperCase();
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));

            byte[] bytePassword = md.digest(password.getBytes());

            return DatatypeConverter.printHexBinary(bytePassword).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("não deu certo");
        }
    }

    public static boolean checkPassword(String password, String salt, String hashedPassword) {
        String passwordVerify = hashPassword(password, salt);
         return passwordVerify.equals(hashedPassword);

    }
}