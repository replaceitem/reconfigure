package net.replaceitem.reconfigure.api.serializer;

import net.replaceitem.reconfigure.api.ConfigBuilder;
import net.replaceitem.reconfigure.config.serialization.Serializer;

import java.util.function.Consumer;

public interface SerializerBuilder<SELF extends SerializerBuilder<SELF, S, C>, S extends Serializer<?, C>, C> {
    /**
     * Adds a preload middleware to the serializer.
     * This is run just before data is loaded from disk into the config.
     * This can be used to migrate old config data to a new format.
     * @see Serializer
     * @param preLoad The consumer for the preload middleware
     * @return The builder for chaining
     */
    SELF preLoad(Consumer<C> preLoad);

    /**
     * Adds a prewrite middleware to the serializer.
     * This is run just before data is saved to disk after it has been read from the config.
     * @see Serializer
     * @param preWrite The consumer for the prewrite middleware
     * @return The builder for chaining
     */
    SELF preWrite(Consumer<C> preWrite);

    /**
     * Finishes building the serializer
     * @return The build serializer instance. This should be provided to {@link ConfigBuilder#serializer(Serializer)}
     */
    S build();
}
