package net.replaceitem.reconfigure.api;

public class ValidationResult {
    private static final ValidationResult VALID = new ValidationResult("");

    private final String message;

    private ValidationResult(String message) {
        this.message = message;
    }

    public static ValidationResult valid() {
        return VALID;
    }

    public static ValidationResult invalid(String error) {
        return new ValidationResult(error);
    }
    public boolean isValid() {
        return this == VALID;
    }

    public boolean isInvalid() {
        return !this.isValid();
    }

    public String getMessage() {
        return message;
    }
}
