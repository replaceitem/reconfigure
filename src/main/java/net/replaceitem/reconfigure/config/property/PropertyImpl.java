package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.Property;

public class PropertyImpl<T> implements Property<T> {
    protected T value;
    private final Identifier id;
    private final T defaultValue;

    public PropertyImpl(Identifier id, T defaultValue) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
    
    @Override
    public T get() {
        return value;
    }
    
    @Override
    public void set(T value) {
        this.value = value;
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
    public Identifier getId() {
        return id;
    }
}
