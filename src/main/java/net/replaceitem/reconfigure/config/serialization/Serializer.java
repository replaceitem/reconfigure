package net.replaceitem.reconfigure.config.serialization;

import com.google.gson.JsonParseException;
import net.replaceitem.reconfigure.config.ConfigImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Serializer {
    public abstract void write(ConfigImpl config, OutputStream outputStream) throws IOException, JsonParseException;
    public abstract void read(ConfigImpl config, InputStream inputStream) throws IOException, JsonParseException;
    public abstract String getFileExtension();
}
