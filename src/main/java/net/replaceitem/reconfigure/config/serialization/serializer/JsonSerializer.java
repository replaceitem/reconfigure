package net.replaceitem.reconfigure.config.serialization.serializer;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import net.replaceitem.reconfigure.config.serialization.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonSerializer extends CharSerializer<JsonElement> {
    
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();
    
    private static final JsonMarshaller MARSHALLER = new JsonMarshaller();

    @Override
    public Marshaller<JsonElement> getMarshaller() {
        return MARSHALLER;
    }

    @Override
    public void write(SerializationTarget target, Writer writer) throws IOException, JsonParseException {
        JsonObject jsonObject = new JsonObject();
        for (SerializationTarget.SerializationProperty<?> property : target.getProperties()) {
            JsonElement jsonElement = this.getProperty(property);
            jsonObject.add(property.getId().getPath(), jsonElement);
        }
        try(JsonWriter jsonWriter = GSON.newJsonWriter(writer)) {
            GSON.toJson(jsonObject, jsonWriter);
        }
    }

    @Override
    public void read(SerializationTarget target, Reader reader) throws IOException, JsonParseException {
        JsonElement jsonElement;
        try(JsonReader jsonReader = GSON.newJsonReader(reader)) {
            jsonElement = JsonParser.parseReader(jsonReader);
        }
        if(!(jsonElement instanceof JsonObject jsonObject)) throw new JsonParseException("Expected a json object");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            this.setProperty(target, entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String getFileExtension() {
        return "json";
    }

    private static class JsonMarshaller extends Marshaller<JsonElement> {

        @Override
        public JsonElement marshall(Intermediary<?> intermediary) {
            return switch (intermediary) {
                case Intermediary.IntermediaryString intermediaryString -> new JsonPrimitive(intermediaryString.getValue());
                case Intermediary.IntermediaryInteger intermediaryInteger -> new JsonPrimitive(intermediaryInteger.getValue());
                case Intermediary.IntermediaryDouble intermediaryDouble -> new JsonPrimitive(intermediaryDouble.getValue());
                case Intermediary.IntermediaryBoolean intermediaryBoolean -> new JsonPrimitive(intermediaryBoolean.getValue());
                case Intermediary.IntermediaryList intermediaryList -> JsonOps.INSTANCE.createList(intermediaryList.getValue().stream().map(JsonPrimitive::new));
            };
        }

        @Override
        protected String unmarshallString(JsonElement value) throws SerializationException {
            if(!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) throw new SerializationException("Expected a string");
            return value.getAsString();
        }

        @Override
        protected Integer unmarshallInteger(JsonElement value) throws SerializationException {
            if(!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) throw new SerializationException("Expected an integer");
            return value.getAsInt();
        }

        @Override
        protected Double unmarshallDouble(JsonElement value) throws SerializationException {
            if(!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isNumber()) throw new SerializationException("Expected a double");
            return value.getAsDouble();
        }

        @Override
        protected Boolean unmarshallBoolean(JsonElement value) throws SerializationException {
            if(!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isBoolean()) throw new SerializationException("Expected a boolean");
            return value.getAsBoolean();
        }

        @Override
        protected List<String> unmarshallList(JsonElement value) throws SerializationException {
            if(!value.isJsonArray()) throw new SerializationException("Expected a list");
            return new ArrayList<>(value.getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList());
        }
    }
}
