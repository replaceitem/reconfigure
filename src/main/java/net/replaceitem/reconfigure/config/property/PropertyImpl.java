package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.Property;
import net.replaceitem.reconfigure.api.ValidationResult;
import net.replaceitem.reconfigure.config.ValidatorList;

public class PropertyImpl<T> implements Property<T> {
    protected T value;
    private final Identifier id;
    private final T defaultValue;
    private final ValidatorList<T> validators;

    public PropertyImpl(Identifier id, T defaultValue, ValidatorList<T> validators) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.validators = validators;
        
        if(this.validate(defaultValue).isInvalid()) throw new IllegalArgumentException("The default value " + defaultValue + " of property " + id + " is invalid with the provided validators");
    }
    
    @Override
    public T get() {
        return value;
    }
    
    @Override
    public void set(T value) {
        ValidationResult result = this.validate(value);
        if(result.isInvalid()) throw new IllegalArgumentException("Cannot set property " + id + " to " + value + ": " + result.getMessage());
        this.value = value;
    }
    
    @Override
    public void setIfValid(T value) {
        if(this.validate(value).isValid()) this.value = value;
    }
    
    @Override
    public void setOrDefault(T value) {
        this.value = this.validate(value).isValid() ? value : this.defaultValue;
    }
    
    @Override
    public void reset() {
        this.set(defaultValue);
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }
    
    public ValidationResult validate(T value) {
        return this.validators.validate(value);
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
