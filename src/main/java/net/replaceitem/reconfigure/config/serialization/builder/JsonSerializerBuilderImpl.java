package net.replaceitem.reconfigure.config.serialization.builder;

import com.google.gson.JsonObject;
import net.replaceitem.reconfigure.api.serializer.JsonSerializerBuilder;
import net.replaceitem.reconfigure.config.serialization.serializer.JsonSerializer;

public class JsonSerializerBuilderImpl extends SerializerBuilderImpl<JsonSerializerBuilder, JsonSerializer, JsonObject> implements JsonSerializerBuilder {
    @Override
    public JsonSerializer build() {
        return new JsonSerializer(this.preLoad, this.preWrite);
    }
}
