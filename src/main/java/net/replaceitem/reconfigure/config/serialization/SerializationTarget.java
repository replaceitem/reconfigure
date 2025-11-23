package net.replaceitem.reconfigure.config.serialization;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface SerializationTarget {
    @Nullable SerializationProperty<?> getProperty(ResourceLocation key);
    @Nullable SerializationProperty<?> getProperty(String key);
    Collection<? extends SerializationProperty<?>> getProperties();
    String getNamespace();

    interface SerializationProperty<T> {
        TypeAdapter<T,?> getTypeAdapter();
        void setOrDefault(T value);

        T get();
        ResourceLocation getId();
    }
}
