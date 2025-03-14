package net.replaceitem.reconfigure.api.serializer;

import net.replaceitem.reconfigure.config.serialization.Serializer;

import java.util.function.Consumer;

public interface SerializerBuilder<SELF extends SerializerBuilder<SELF, S, C>, S extends Serializer<?, C>, C> {
    SELF preLoad(Consumer<C> preLoad);
    SELF preWrite(Consumer<C> preWrite);
    S build();
}
