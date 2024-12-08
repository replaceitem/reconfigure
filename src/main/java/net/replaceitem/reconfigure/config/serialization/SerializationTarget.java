package net.replaceitem.reconfigure.config.serialization;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface SerializationTarget {
    @Nullable SerializationProperty<?> getProperty(Identifier key);
    @Nullable SerializationProperty<?> getProperty(String key);
    Collection<? extends SerializationProperty<?>> getProperties();
    String getNamespace();

    interface SerializationProperty<T> {
        TypeAdapter<T,?> getTypeAdapter();
        void set(T value);
        T get();
        Identifier getId();
    }
}
