package net.replaceitem.reconfigure.config.serialization;

import com.google.gson.JsonParseException;
import net.replaceitem.reconfigure.config.ConfigImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class CharSerializer extends Serializer {
    
    public abstract void write(ConfigImpl config, Writer writer) throws IOException, JsonParseException;
    public abstract void read(ConfigImpl config, Reader writer) throws IOException, JsonParseException;
    
    @Override
    public void write(ConfigImpl config, OutputStream outputStream) throws JsonParseException, IOException {
        Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        write(config, writer);
    }

    @Override
    public void read(ConfigImpl config, InputStream inputStream) throws JsonParseException, IOException {
        Reader reader = new InputStreamReader(inputStream);
        read(config, reader);
    }
}
