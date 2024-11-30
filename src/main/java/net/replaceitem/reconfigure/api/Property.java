package net.replaceitem.reconfigure.api;

import net.minecraft.util.Identifier;

public interface Property<T> {
    T get();
    void set(T value);
    void reset();
    T getDefaultValue();
    Identifier getId();
}