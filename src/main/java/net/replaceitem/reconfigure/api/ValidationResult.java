package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;

public class ValidationResult {
    private static final ValidationResult VALID = new ValidationResult(Text.empty());

    private final Text message;

    private ValidationResult(Text message) {
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
    public static ValidationResult invalid(Text error) {
        return new ValidationResult(error);
    }

    /**
     * @param error The error string for the invalid validation result, which will be used as a literal text.
     * @return An invalid validation result
     */
    public static ValidationResult invalid(String error) {
        return new ValidationResult(Text.literal(error));
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
     * @return The validation error message if invalid. If valid, returns {@link Text#empty()}
     */
    public Text getMessage() {
        return message;
    }
}
