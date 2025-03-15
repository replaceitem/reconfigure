package net.replaceitem.reconfigure.config.serialization.builder;

import net.replaceitem.reconfigure.api.serializer.SerializerBuilder;
import net.replaceitem.reconfigure.config.serialization.Serializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class SerializerBuilderImpl<SELF extends SerializerBuilder<SELF, S, C>, S extends Serializer<?,C>, C> implements SerializerBuilder<SELF, S, C> {
    protected @Nullable Consumer<C> preLoad;
    protected @Nullable Consumer<C> preWrite;
    
    @Override
    public SELF preRead(Consumer<C> preRead) {
        this.preLoad = preRead;
        return self();
    }
    
    @Override
    public SELF preWrite(Consumer<C> preWrite) {
        this.preWrite = preWrite;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
