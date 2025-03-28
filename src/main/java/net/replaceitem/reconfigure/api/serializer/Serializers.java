package net.replaceitem.reconfigure.api.serializer;

import net.replaceitem.reconfigure.config.serialization.builder.JsonSerializerBuilderImpl;
import net.replaceitem.reconfigure.config.serialization.builder.NbtSerializerBuilderImpl;
import net.replaceitem.reconfigure.config.serialization.builder.PropertiesSerializerBuilderImpl;
import net.replaceitem.reconfigure.config.serialization.serializer.JsonSerializer;
import net.replaceitem.reconfigure.config.serialization.serializer.NbtSerializer;
import net.replaceitem.reconfigure.config.serialization.serializer.PropertiesSerializer;

public class Serializers {
    public static final JsonSerializer JSON = new JsonSerializer(null, null);
    public static final PropertiesSerializer PROPERTIES = new PropertiesSerializer(null, null);
    public static final NbtSerializer NBT = new NbtSerializer(null, null);
    
    /**
     * Starts building a JSON serializer
     * @return The json serializer builder
     */
    public static JsonSerializerBuilder buildJson() {
        return new JsonSerializerBuilderImpl();
    }

    /**
     * Starts building a NBT serializer
     * @return The nbt serializer builder
     */
    public static NbtSerializerBuilder buildNbt() {
        return new NbtSerializerBuilderImpl();
    }

    /**
     * Starts building a {@link java.util.Properties Properties} serializer
     * @return The properties serializer builder
     */
    public static PropertiesSerializerBuilder buildProperties() {
        return new PropertiesSerializerBuilderImpl();
    }
    
    private Serializers() {}
}
