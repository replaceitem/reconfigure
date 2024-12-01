package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.Reconfigure;
import net.replaceitem.reconfigure.api.Property;
import net.replaceitem.reconfigure.config.serialization.Caster;
import net.replaceitem.reconfigure.config.serialization.CastingException;

public class PropertyImpl<T> implements Property<T> {
    protected T value;
    private final Identifier id;
    private final T defaultValue;
    private final Caster<T> caster;

    public PropertyImpl(Identifier id, T defaultValue, Caster<T> caster) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.caster = caster;
    }
    
    @Override
    public T get() {
        return value;
    }
    
    @Override
    public void set(T value) {
        this.value = value;
    }
    
    public void setFromObject(Object val) {
        if(val == null) {
            Reconfigure.LOGGER.error("Could not set property {} to null", id);
            return;
        }
        try {
            this.set(this.caster.cast(val));
        } catch (CastingException e) {
            Reconfigure.LOGGER.error("Could not set property {} to {} (type {})", id, val, val.getClass().getSimpleName(), e);
        }
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
