package net.replaceitem.reconfigure.config.serialization;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.Reconfigure;
import net.replaceitem.reconfigure.config.ConfigImpl;
import net.replaceitem.reconfigure.config.property.PropertyImpl;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public class JsonSerializer extends CharSerializer {
    
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();

    @Override
    public void write(ConfigImpl config, Writer writer) throws IOException, JsonParseException {
        JsonObject jsonObject = new JsonObject();
        for (PropertyImpl<?> property : config.getProperties().values()) {
            jsonObject.add(property.getId().getPath(), GSON.toJsonTree(property.get()));
        }
        try(JsonWriter jsonWriter = GSON.newJsonWriter(writer)) {
            GSON.toJson(jsonObject, jsonWriter);
        }
    }

    @Override
    public void read(ConfigImpl config, Reader reader) throws IOException, JsonParseException {
        Map<Identifier, PropertyImpl<?>> properties = config.getProperties();
        JsonElement jsonElement;
        try(JsonReader jsonReader = GSON.newJsonReader(reader)) {
            jsonElement = JsonParser.parseReader(jsonReader);
        }
        if(!(jsonElement instanceof JsonObject jsonObject)) throw new JsonParseException("Expected a json object");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Identifier identifier = Identifier.of(config.getNamespace(), entry.getKey());
            PropertyImpl<?> property = properties.get(identifier);
            if(property == null) {
                Reconfigure.LOGGER.info("Could not load property {} from file because it is not defined in the config", identifier);
                continue;
            }
            Object object = GSON.fromJson(entry.getValue(), Object.class);
            property.setFromObject(object);
        }
    }

    @Override
    public String getFileExtension() {
        return "json";
    }
}
