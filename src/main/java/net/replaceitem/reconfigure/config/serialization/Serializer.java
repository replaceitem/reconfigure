package net.replaceitem.reconfigure.config.serialization;

import net.replaceitem.reconfigure.Reconfigure;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * Base class for implementing serialization and deserialization strategies for a config.
 * Serialization and deserialization happen in two stages:
 * <table>
 *     <caption>Serialization stages</caption>
 *     <tr>
 *         <th>File</th>
 *         <th></th>
 *         <th>{@link C}</th>
 *         <th></th>
 *         <th>Config</th>
 *     </tr>
 *     <tr>
 *         <td>O</td>
 *         <td>---{@link #read read}---&gt;</td>
 *         <td>O</td>
 *         <td>---{@link #load load}---&gt;</td>
 *         <td>O</td>
 *     </tr>
 *     <tr>
 *         <td>O</td>
 *         <td>&lt;--{@link #write write}---</td>
 *         <td>O</td>
 *         <td>&lt;--{@link #save save}---</td>
 *         <td>O</td>
 *     </tr>
 * </table>
 * 
 * In the middle stage, middleware may be added in the form of {@link #preLoad} and {@link #preWrite} in
 * order for the mod using the configuration library to do custom handling of data (such as datafixing).
 *
 * @param <T> The type of one property after reading, as provided to the {@link Marshaller},
 *           such as a {@link com.google.gson.JsonElement} or {@link net.minecraft.nbt.NbtElement}.
 * @param <C> The type of the complete compound object after reading, such as {@link com.google.gson.JsonObject}.
 *           A value of this type will be provided to middleware.
 */
public abstract class Serializer<T,C> {
    @Nullable private final Consumer<C> preLoad;
    @Nullable private final Consumer<C> preWrite;

    protected Serializer(@Nullable Consumer<C> preLoad, @Nullable Consumer<C> preWrite) {
        this.preLoad = preLoad;
        this.preWrite = preWrite;
    }

    protected abstract void write(OutputStream outputStream, C compound) throws IOException;
    protected abstract C read(InputStream inputStream) throws IOException;
    protected abstract void load(SerializationTarget target, C compound) throws IOException;
    protected abstract C save(SerializationTarget target) throws IOException;
    
    public abstract String getFileExtension();
    public abstract Marshaller<T> getMarshaller();
    
    public void serialize(SerializationTarget serializationTarget, OutputStream outputStream) throws IOException {
        C saved = this.save(serializationTarget);
        if(preWrite != null) this.preWrite.accept(saved);
        this.write(outputStream, saved);
    }
    
    public void deserialize(SerializationTarget serializationTarget, InputStream outputStream) throws IOException {
        C read = this.read(outputStream);
        if(preLoad != null) this.preLoad.accept(read);
        this.load(serializationTarget, read);
    }

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
