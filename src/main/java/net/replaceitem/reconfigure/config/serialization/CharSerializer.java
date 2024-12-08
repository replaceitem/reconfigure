package net.replaceitem.reconfigure.config.serialization;

import com.google.gson.JsonParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class CharSerializer<T> extends Serializer<T> {
    
    public abstract void write(SerializationTarget target, Writer writer) throws IOException, JsonParseException;
    public abstract void read(SerializationTarget target, Reader writer) throws IOException, JsonParseException;
    
    @Override
    public void write(SerializationTarget target, OutputStream outputStream) throws JsonParseException, IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        write(target, writer);
    }

    @Override
    public void read(SerializationTarget target, InputStream inputStream) throws JsonParseException, IOException {
        Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        read(target, reader);
    }
}
