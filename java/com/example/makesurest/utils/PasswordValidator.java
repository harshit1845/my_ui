package com.example.makesurest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    public static boolean isPasswordValid(String password) {
        // Check length
        if (password.length() < 8) {
            return false;
        }

        // Check for at least one special character
        if (!containsSpecialCharacter(password)) {
            return false;
        }

        // All conditions passed
        return true;
    }

    private static boolean containsSpecialCharacter(String password) {
        Pattern pattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
