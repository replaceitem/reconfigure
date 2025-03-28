package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;

import java.util.function.Predicate;

@FunctionalInterface
public interface Validator<T> {
    /**
     * @param value The value to validate
     * @return The validation result for the provided value
     */
    ValidationResult validate(T value);

    /**
     * Creates a validator from a {@link Predicate} and an error message.
     * @param predicate    The predicate for checking whether a value is valid.
     *                     Should return {@code true} for valid values
     * @param errorMessage The error message for invalid values
     * @return The validator
     */
    static <T> Validator<T> ofPredicate(Predicate<T> predicate, Text errorMessage) {
        return value -> predicate.test(value) ? ValidationResult.valid() : ValidationResult.invalid(errorMessage);
    }
}
