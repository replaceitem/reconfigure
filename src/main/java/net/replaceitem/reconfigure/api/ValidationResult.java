package net.replaceitem.reconfigure.api;

import net.minecraft.network.chat.Component;

public class ValidationResult {
    private static final ValidationResult VALID = new ValidationResult(Component.empty());

    private final Component message;

    private ValidationResult(Component message) {
        this.message = message;
    }

    /**
     * @return A valid validation result
     */
    public static ValidationResult valid() {
        return VALID;
    }

    /**
     * @param error The error text for the invalid validation result
     * @return An invalid validation result
     */
    public static ValidationResult invalid(Component error) {
        return new ValidationResult(error);
    }

    /**
     * @param error The error string for the invalid validation result, which will be used as a literal text.
     * @return An invalid validation result
     */
    public static ValidationResult invalid(String error) {
        return new ValidationResult(Component.literal(error));
    }

    /**
     * @return Whether the validation result is valid
     */
    public boolean isValid() {
        return this == VALID;
    }

    /**
     * @return Whether the validation result is invalid
     */
    public boolean isInvalid() {
        return !this.isValid();
    }

    /**
     * @return The validation error message if invalid. If valid, returns {@link Component#empty()}
     */
    public Component getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return isValid() ? "Valid" : getMessage().getString();
    }
}
