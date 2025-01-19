package net.replaceitem.reconfigure.config.serialization;

import net.replaceitem.reconfigure.Reconfigure;

import java.util.List;

import static net.replaceitem.reconfigure.config.serialization.IntermediaryType.*;

/**
 * This class converts from a Serialization specific format
 * (e.g. {@link com.google.gson.JsonElement} or {@link net.minecraft.nbt.NbtElement})
 * to an {@link Intermediary} and back. Each Serializer has its own Marshaller.
 * The {@link net.replaceitem.reconfigure.config.serialization.serializer.JsonSerializer}
 * for example uses {@link com.google.gson.JsonElement}. The Marshaller for Json would thus
 * convert from JsonElement to an {@link Intermediary}.
 * @param <T> The type the Serializer expects for serialization.
 */
public abstract class Marshaller<T> {
    public abstract T marshall(Intermediary<?> intermediary);
    
    public <M extends Intermediary<?>> M unmarshall(T value, IntermediaryType<M> type) throws SerializationException {
        if(type == STRING) return type.cast(new Intermediary.IntermediaryString(this.unmarshallString(value)));
        if(type == INTEGER) return type.cast(new Intermediary.IntermediaryInteger(this.unmarshallInteger(value)));
        if(type == DOUBLE) return type.cast(new Intermediary.IntermediaryDouble(this.unmarshallDouble(value)));
        if(type == BOOLEAN) return type.cast(new Intermediary.IntermediaryBoolean(this.unmarshallBoolean(value)));
        if(type == LIST) return type.cast(new Intermediary.IntermediaryList(this.unmarshallList(value)));
        throw new SerializationException("Cannot unmarshall to type " + type);
    }

    protected abstract String unmarshallString(T value) throws SerializationException;
    protected abstract Integer unmarshallInteger(T value) throws SerializationException;
    protected abstract Double unmarshallDouble(T value) throws SerializationException;
    protected abstract Boolean unmarshallBoolean(T value) throws SerializationException;
    protected abstract List<String> unmarshallList(T value) throws SerializationException;


    public <P> void setProperty(SerializationTarget.SerializationProperty<P> holder, T value) {
        TypeAdapter<P, ?> typeAdapter = holder.getTypeAdapter();
        try {
            P propertyValue = convertToProperty(typeAdapter, value);
            holder.setOrDefault(propertyValue);
        } catch (SerializationException e) {
            Reconfigure.LOGGER.error("Could not set property {}", holder.getId(), e);
        }
    }

    protected <P,M extends Intermediary<?>> P convertToProperty(TypeAdapter<P,M> typeAdapter, T value) throws SerializationException {
        M intermediary = this.unmarshall(value, typeAdapter.getType());
        return typeAdapter.fromIntermediary(intermediary);
    }
    
    public  <P> T getProperty(SerializationTarget.SerializationProperty<P> holder) {
        P propertyValue = holder.get();
        return convertFromProperty(holder.getTypeAdapter(), propertyValue);
    }
    
    protected <P,M extends Intermediary<?>> T convertFromProperty(TypeAdapter<P,M> typeAdapter, P propertyValue) {
        M intermediary = typeAdapter.toIntermediary(propertyValue);
        return this.marshall(intermediary);
    }
}
