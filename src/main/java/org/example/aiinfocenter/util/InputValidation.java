package org.example.aiinfocenter.util;

import org.example.aiinfocenter.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class InputValidation {

    public void requireNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    public void requireMaxLen(String value, String fieldName, int max) {
        if (value != null && value.length() > max) {
            throw new IllegalArgumentException(fieldName + " is too long (max " + max + ")");
        }
    }

    public void validatePassword(String password) {
        requireNotBlank(password, "password");
        if (password.length() < 4) {
            throw new IllegalArgumentException("password must be at least 4 characters");
        }
    }

    public UserRole parseRole(String roleRaw) {
        requireNotBlank(roleRaw, "role");

        try {
            return UserRole.valueOf(roleRaw.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("role must be STUDENT / ADMIN");
        }
    }

    public void validateStudentFields(String faculty, Integer year) {
        requireNotBlank(faculty, "faculty");
        if (year == null || year < 1 || year > 10) {
            throw new IllegalArgumentException("yearOfStudy must be 1..10");
        }
    }

    public void validateMessage(String message) {
        requireNotBlank(message, "message");
        requireMaxLen(message, "message", 1000);
    }

    public void validateTitle(String title) {
        requireNotBlank(title, "title");
        requireMaxLen(title, "title", 100);
    }
}
