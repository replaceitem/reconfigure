package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.config.AbstractBindable;
import net.replaceitem.reconfigure.api.Property;
import net.replaceitem.reconfigure.api.ValidationResult;
import net.replaceitem.reconfigure.config.ValidatorList;

import java.util.Objects;

public class PropertyImpl<T> extends AbstractBindable<T> implements Property<T> {
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
    
    private void setInternal(T value) {
        if(!Objects.equals(this.value, value)) {
            this.value = value;
            this.callListeners(this.value);
        }
    }
    
    @Override
    public void set(T value) {
        ValidationResult result = this.validate(value);
        if(result.isInvalid()) throw new IllegalArgumentException("Cannot set property " + id + " to " + value + ": " + result.getMessage());
        this.setInternal(value);
    }
    
    @Override
    public void setIfValid(T value) {
        if(this.validate(value).isValid()) this.setInternal(value);
    }
    
    @Override
    public void setOrDefault(T value) {
        this.setInternal(this.validate(value).isValid() ? value : this.defaultValue);
    }
    
    @Override
    public void reset() {
        this.set(defaultValue);
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }
    
    @Override
    public boolean isDefault() {
        return this.isDefault(this.value);
    }
    
    @Override
    public boolean isDefault(T value) {
        return Objects.equals(value, this.defaultValue);
    }

    public ValidationResult validate(T value) {
        return this.validators.validate(value);
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
