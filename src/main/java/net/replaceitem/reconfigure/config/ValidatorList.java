package net.replaceitem.reconfigure.config;

import net.replaceitem.reconfigure.api.ValidationResult;
import net.replaceitem.reconfigure.api.Validator;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ValidatorList<T> {
    @Nullable private List<Validator<T>> validators;
    
    public void add(Validator<T> validator) {
        if(this.validators == null) this.validators = new ArrayList<>(3);
        this.validators.add(validator);
    }
    
    public ValidationResult validate(T value) {
        if(this.validators == null) return ValidationResult.valid();
        for (Validator<T> validator : this.validators) {
            ValidationResult result = validator.validate(value);
            if(result.isInvalid()) return result;
        }
        return ValidationResult.valid();
    }
}
