package net.replaceitem.reconfigure.config.serialization;

import com.google.gson.JsonParseException;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Intermediary serializer class for serialization methods using {@link Reader} or {@link Writer}.
 * Such serializers should extend this class instead of {@link Serializer}
 */
public abstract class CharSerializer<T,C> extends Serializer<T,C> {

    public CharSerializer(@Nullable Consumer<C> preLoad, @Nullable Consumer<C> preWrite) {
        super(preLoad, preWrite);
    }

    protected abstract void write(Writer writer, C compound) throws IOException;
    protected abstract C read(Reader reader) throws IOException, JsonParseException;
    
    @Override
    protected void write(OutputStream outputStream, C compound) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        write(writer, compound);
    }

    @Override
    protected C read(InputStream inputStream) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        return read(reader);
    }
}
