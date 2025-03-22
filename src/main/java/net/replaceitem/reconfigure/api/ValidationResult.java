package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;

public class ValidationResult {
    private static final ValidationResult VALID = new ValidationResult(Text.empty());

    private final Text message;

    private ValidationResult(Text message) {
        this.message = message;
    }

    public static ValidationResult valid() {
        return VALID;
    }

    public static ValidationResult invalid(Text error) {
        return new ValidationResult(error);
    }
    public static ValidationResult invalid(String error) {
        return new ValidationResult(Text.literal(error));
    }
    public boolean isValid() {
        return this == VALID;
    }

    public boolean isInvalid() {
        return !this.isValid();
    }

    public Text getMessage() {
        return message;
    }
}
