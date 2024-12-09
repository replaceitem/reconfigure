package net.replaceitem.reconfigure.api;

import net.replaceitem.reconfigure.config.serialization.serializer.JsonSerializer;
import net.replaceitem.reconfigure.config.serialization.serializer.NbtSerializer;
import net.replaceitem.reconfigure.config.serialization.serializer.PropertiesSerializer;

public class Serializers {
    public static final JsonSerializer JSON = new JsonSerializer();
    public static final PropertiesSerializer PROPERTIES = new PropertiesSerializer();
    public static final NbtSerializer NBT = new NbtSerializer();
}
