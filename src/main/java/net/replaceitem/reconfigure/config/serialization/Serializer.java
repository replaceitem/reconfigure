package net.replaceitem.reconfigure.config.serialization;

import com.google.gson.JsonParseException;
import net.replaceitem.reconfigure.Reconfigure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Serializer<T> {
    public abstract void write(SerializationTarget target, OutputStream outputStream) throws IOException, JsonParseException;
    public abstract void read(SerializationTarget target, InputStream inputStream) throws IOException, JsonParseException;
    public abstract String getFileExtension();
    public abstract Marshaller<T> getMarshaller();

    protected void setProperty(SerializationTarget target, String key, T value) {
        SerializationTarget.SerializationProperty<?> property = target.getProperty(key);
        if(property == null) {
            Reconfigure.LOGGER.info("Could not load property {} from file because it is not defined in the config", key);
            return;
        }
        this.getMarshaller().setProperty(property, value);
    }

    protected T getProperty(SerializationTarget.SerializationProperty<?> property) {
        return this.getMarshaller().getProperty(property);
    }
}
