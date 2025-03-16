package net.replaceitem.reconfigure.api;

import net.minecraft.util.Identifier;

public interface Property<T> extends Bindable<T> {
    void set(T value);
    void setIfValid(T value);
    void setOrDefault(T value);

    void reset();
    T getDefaultValue();
    boolean isDefault();
    boolean isDefault(T value);

    Identifier getId();
}
