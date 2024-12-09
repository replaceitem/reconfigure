package net.replaceitem.reconfigure.config.serialization.serializer;

import com.google.gson.JsonParseException;
import net.minecraft.nbt.*;
import net.replaceitem.reconfigure.config.serialization.*;

import java.io.*;

public class NbtSerializer extends Serializer<NbtElement> {
    
    private static final NbtMarshaller MARSHALLER = new NbtMarshaller();
    
    @Override
    public void write(SerializationTarget target, OutputStream outputStream) throws IOException, JsonParseException {
        NbtCompound nbtCompound = new NbtCompound();
        for (SerializationTarget.SerializationProperty<?> property : target.getProperties()) {
            NbtElement value = this.getProperty(property);
            nbtCompound.put(property.getId().getPath(), value);
        }
        NbtIo.writeCompound(nbtCompound, new DataOutputStream(outputStream));
    }

    @Override
    public void read(SerializationTarget target, InputStream inputStream) throws IOException, JsonParseException {
        NbtCompound nbt = NbtIo.readCompound(new DataInputStream(inputStream), NbtSizeTracker.ofUnlimitedBytes());
        for (String key : nbt.getKeys()) {
            NbtElement value = nbt.get(key);
            if(value == null) continue;
            this.setProperty(target, key, value);
        }
    }

    @Override
    public String getFileExtension() {
        return "nbt";
    }

    @Override
    public Marshaller<NbtElement> getMarshaller() {
        return MARSHALLER;
    }
    
    private static class NbtMarshaller extends Marshaller<NbtElement> {

        @Override
        public NbtElement marshall(Intermediary<?> intermediary) {
            return switch (intermediary) {
                case Intermediary.IntermediaryString intermediaryString -> NbtString.of(intermediaryString.getValue());
                case Intermediary.IntermediaryInteger intermediaryInteger -> NbtInt.of(intermediaryInteger.getValue());
                case Intermediary.IntermediaryDouble intermediaryDouble -> NbtDouble.of(intermediaryDouble.getValue());
                case Intermediary.IntermediaryBoolean intermediaryBoolean -> NbtByte.of(intermediaryBoolean.getValue());
            };
        }

        @Override
        protected String unmarshallString(NbtElement value) throws SerializationException {
            if(!(value instanceof NbtString nbtString)) throw new SerializationException("Expected a string");
            return nbtString.asString();
        }

        @Override
        protected Integer unmarshallInteger(NbtElement value) throws SerializationException {
            if (!(value instanceof AbstractNbtNumber nbtNumber)) throw new SerializationException("Expected a number");
            return nbtNumber.intValue();
        }

        @Override
        protected Double unmarshallDouble(NbtElement value) throws SerializationException {
            if (!(value instanceof AbstractNbtNumber nbtNumber)) throw new SerializationException("Expected a number");
            return nbtNumber.doubleValue();
        }

        @Override
        protected Boolean unmarshallBoolean(NbtElement value) throws SerializationException {
            if (!(value instanceof NbtByte nbtNumber)) throw new SerializationException("Expected a byte");
            return nbtNumber.byteValue() != 0;
        }
    }
}
