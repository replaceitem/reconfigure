package net.replaceitem.reconfigure.config.serialization.serializer;

import net.minecraft.nbt.*;
import net.replaceitem.reconfigure.config.serialization.*;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NbtSerializer extends Serializer<NbtElement, NbtCompound> {
    
    private static final NbtMarshaller MARSHALLER = new NbtMarshaller();

    public NbtSerializer(@Nullable Consumer<NbtCompound> preLoad, @Nullable Consumer<NbtCompound> preWrite) {
        super(preLoad, preWrite);
    }

    @Override
    protected void write(OutputStream outputStream, NbtCompound compound) throws IOException {
        NbtIo.writeCompound(compound, new DataOutputStream(outputStream));
    }

    @Override
    protected NbtCompound read(InputStream inputStream) throws IOException {
        return NbtIo.readCompound(new DataInputStream(inputStream), NbtSizeTracker.ofUnlimitedBytes());
    }

    @Override
    protected void load(SerializationTarget target, NbtCompound compound) {
        for (String key : compound.getKeys()) {
            NbtElement value = compound.get(key);
            if(value == null) continue;
            this.setProperty(target, key, value);
        }
    }

    @Override
    protected NbtCompound save(SerializationTarget target) {
        NbtCompound nbtCompound = new NbtCompound();
        for (SerializationTarget.SerializationProperty<?> property : target.getProperties()) {
            NbtElement value = this.getProperty(property);
            nbtCompound.put(property.getId().getPath(), value);
        }
        return nbtCompound;
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
                case Intermediary.IntermediaryList intermediaryList -> createStringList(intermediaryList.getValue());
            };
        }

        @Override
        protected String unmarshallString(NbtElement value) throws SerializationException {
            return value.asString().orElseThrow(() -> new SerializationException("Expected a string"));
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

        @Override
        protected List<String> unmarshallList(NbtElement value) throws SerializationException {
            if (!(value instanceof NbtList nbtList)) throw new SerializationException("Expected a list");
            return new ArrayList<>(nbtList.stream().map(NbtElement::toString).toList());
        }

        private static NbtList createStringList(List<String> strings) {
            NbtList nbtList = new NbtList();
            nbtList.addAll(strings.stream().map(NbtString::of).toList());
            return nbtList;
        }
    }
}
