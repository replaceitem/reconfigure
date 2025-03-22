package net.replaceitem.reconfigure.api;

import net.minecraft.text.Text;

import java.util.function.Predicate;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult validate(T value);
    
    static <T> Validator<T> ofPredicate(Predicate<T> predicate, Text errorMessage) {
        return value -> predicate.test(value) ? ValidationResult.valid() : ValidationResult.invalid(errorMessage);
    }
}
