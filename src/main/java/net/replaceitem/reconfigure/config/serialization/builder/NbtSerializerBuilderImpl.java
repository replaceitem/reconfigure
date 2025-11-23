package net.replaceitem.reconfigure.config.serialization.builder;

import net.minecraft.nbt.CompoundTag;
import net.replaceitem.reconfigure.api.serializer.NbtSerializerBuilder;
import net.replaceitem.reconfigure.config.serialization.serializer.NbtSerializer;

public class NbtSerializerBuilderImpl extends SerializerBuilderImpl<NbtSerializerBuilder, NbtSerializer, CompoundTag> implements NbtSerializerBuilder {
    @Override
    public NbtSerializer build() {
        return new NbtSerializer(this.preLoad, this.preWrite);
    }
}
